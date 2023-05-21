import os
import time
import unittest
from selenium import webdriver
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

# for the following line to perform correctly the root path should be the folder that contain the test.py file 
# which is "Software-Testing-Assi3-Frontend"
indexUrl = "file://" + (os.path.abspath("todo.html")).replace("\\", "/")

print(indexUrl)
def openBrowser(driver):
    driver.maximize_window()
    driver.get(indexUrl)
    time.sleep(5)


def submitTask(driver, taskName, taskDesc):
    # Enter task details
    nameBox = driver.find_element("id", "todo")
    descBox = driver.find_element("id", "desc")
    nameBox.send_keys(taskName)
    time.sleep(2)
    descBox.send_keys(taskDesc)
    time.sleep(2)
    # Click submit button
    addButton = driver.find_element("xpath", '//*[@id="todo-form"]/button')
    addButton.click()
    time.sleep(3)


class TestSuite(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Chrome()

    def test_new_todo(self):
        driver = self.driver
        # Open window
        openBrowser(driver)
        # Submit task
        taskName = "first task"
        taskDesc = "this is the first task in the system"
        submitTask(driver, taskName, taskDesc)
        # Check the last entry
        table = driver.find_element("id", "todo-table")
        rows = table.find_elements("xpath", ".//tr")
        numRows = len(rows)
        lastRow = driver.find_element("id", "row-" + str(numRows)).find_elements("xpath", ".//td")
        assert taskName == lastRow[1].text
        assert taskDesc == lastRow[2].text
        # Check text boxes
        textBoxes = driver.find_elements("xpath", "//input[@type='text']")
        for tb in textBoxes:
            assert tb.text == ""

    def test_delete_todo(self):
        driver = self.driver
        # Open window
        openBrowser(driver)
        # Submit task
        taskName = "deleted task"
        taskDesc = "this task should be deleted right now"
        submitTask(driver, taskName, taskDesc)
        # Delete the target entry
        table = driver.find_element("id", "todo-table")
        rows = table.find_elements("xpath", ".//tr")
        numRows = len(rows)
        deleteButton = driver.find_element("xpath", '//*[@id="row-' + str(numRows) + '"]/td[5]/button')
        deleteButton.click()
        time.sleep(3)
        # Search for the task after it was deleted
        for i in range(1, numRows):
            row = driver.find_element("id", "row-" + str(i)).find_elements("xpath", ".//td")
            assert row[1].text != taskName

    def test_update_todo(self):
        driver = self.driver
        # Open window
        openBrowser(driver)

        # Submit task
        taskName = "testing task"
        taskDesc = "this task should be checked right now"
        submitTask(driver, taskName, taskDesc)

        # Attempt to click the checkbox
        table = driver.find_element("id", "todo-table")
        rows = table.find_elements("xpath", ".//tr")
        numRows = len(rows)
        checkBox = driver.find_element("xpath", '//*[@id="checkbox-' + str(numRows) + '"]')
        checkBox.click()
        time.sleep(3)
        # If message box appears, supress it
        try:
            messagebox = WebDriverWait(driver, 3).until(
                EC.presence_of_element_located(("id", "message-box")))
            messagebox.find_element_by_xpath("//button[text()='OK']").click()
        except:
            pass  # do nothing if not found

        # Check the checkbox status
        assert checkBox.is_selected()

    def test_get_all_todos(self):
        driver = self.driver
        # Open window
        openBrowser(driver)
        # Submit four tasks
        taskNames = ["t1","t2","t3","t4"]
        taskDescs = ["d1","d2","d3","d4"]
        for i in range (0, len(taskNames)):
            submitTask(driver, taskNames[i], taskDescs[i])

        # Click ListAll button
        listAllButton = driver.find_element("xpath", "/html/body/div/div/div[2]/button[1]")
        listAllButton.click()
        time.sleep(3)
        # Check entries to make sure that they exist
        # ,although there may be other elements from a previous run or any other reason
        table = driver.find_element("id", "todo-table")
        rows = table.find_elements("xpath", ".//tr")
        numRows = len(rows)
        for i in range(1, numRows):
            row = driver.find_element("id", "row-" + str(i)).find_elements("xpath", ".//td")
            found_name = row[1].text
            found_desc = row[2].text
            try:
                if found_name in taskNames:
                    taskNames = taskNames.remove(found_name)
                    taskDescs = taskDescs.remove(found_desc)
            except:
                pass
        assert taskNames is None or len(taskNames) == 0

    def tearDown(self):
        self.driver.close()


if __name__ == "__main__":
    unittest.main()
