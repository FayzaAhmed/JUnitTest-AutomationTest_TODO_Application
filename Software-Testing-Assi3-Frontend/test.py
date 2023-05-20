import os
import time
import unittest
from selenium import webdriver

indexUrl = "file://" + os.path.abspath("todo.html")
print(indexUrl)


class TestSuite(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()

    def test_new_todo(self):
        # Open window
        driver = self.driver
        driver.maximize_window()
        driver.get(indexUrl)
        time.sleep(1)
        # Enter task details
        nameBox = driver.find_element("id", "todo")
        descBox = driver.find_element("id", "desc")
        taskName = "first task"
        taskDesc = "this is the first task in the system"
        nameBox.send_keys(taskName)
        descBox.send_keys(taskDesc)
        # Click submit button
        addButton = driver.find_element("xpath", '//*[@id="todo-form"]/button')
        addButton.click()
        time.sleep(1)
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
        # Open window
        driver = self.driver
        driver.maximize_window()
        driver.get(indexUrl)
        time.sleep(1)
        # Enter task details
        nameBox = driver.find_element("id", "todo")
        descBox = driver.find_element("id", "desc")
        taskName = "deleted task"
        taskDesc = "this task should be deleted right now"
        nameBox.send_keys(taskName)
        descBox.send_keys(taskDesc)
        # Click submit button
        addButton = driver.find_element("xpath", '//*[@id="todo-form"]/button')
        addButton.click()
        time.sleep(1)
        # delete the target entry
        table = driver.find_element("id", "todo-table")
        rows = table.find_elements("xpath", ".//tr")
        numRows = len(rows)
        deleteButton = driver.find_element("xpath", '//*[@id="row-' + str(numRows) + '"]/td[5]/button')
        deleteButton.click()
        # Search for the task
        for i in range(1, numRows):
            row = driver.find_element("id", "row-" + str(i)).find_elements("xpath", ".//td")
            assert row[1].text != taskName

    def tearDown(self):
        self.driver.close()


if __name__ == "__main__":
    unittest.main(warnings='ignore')
