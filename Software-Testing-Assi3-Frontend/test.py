import os
import time
import unittest
from selenium import webdriver
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

indexUrl = "file://" + os.path.abspath("todo.html")
print(indexUrl)


def openBrowser(driver):
    driver.maximize_window()
    driver.get(indexUrl)
    time.sleep(1)


def submitTask(driver, taskName, taskDesc):
    # Enter task details
    nameBox = driver.find_element("id", "todo")
    descBox = driver.find_element("id", "desc")
    nameBox.send_keys(taskName)
    descBox.send_keys(taskDesc)
    # Click submit button
    addButton = driver.find_element("xpath", '//*[@id="todo-form"]/button')
    addButton.click()
    time.sleep(1)


class TestSuite(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()

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
        time.sleep(15)

        # Attempt to click the checkbox
        table = driver.find_element("id", "todo-table")
        rows = table.find_elements("xpath", ".//tr")
        numRows = len(rows)
        checkBox = driver.find_element("xpath", '//*[@id="checkbox-' + str(numRows) + '"]')
        checkBox.click()

        # If message box appears, supress it
        try:
            messagebox = WebDriverWait(driver, 3).until(
                EC.presence_of_element_located(("id", "message-box")))
            messagebox.find_element_by_xpath("//button[text()='OK']").click()
        except:
            pass  # do nothing if not found

        # Check the checkbox status
        assert checkBox.is_selected()

    def tearDown(self):
        self.driver.close()


if __name__ == "__main__":
    unittest.main(warnings='ignore')
