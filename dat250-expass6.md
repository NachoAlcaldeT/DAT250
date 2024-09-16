# DAT250 Experiment 3 - Activity Report

## Technical Issues Encountered

1. **Difficulty Choosing the Poll to Vote On:**
   Initially, there was an issue with the list of available polls for voting. The list was not displaying correctly, which prevented users from selecting a poll to vote on. This issue was resolved by adjusting the poll loading logic and ensuring that data is correctly updated in the user interface.

2. **Problems with Displaying Poll Options:**
   The biggest challenge was that, after selecting a poll, the possible response options were not displayed. This issue was due to the function that retrieves the options for a poll not handling the server's response correctly. It was resolved by reviewing and adjusting the `fetchPollOptions` function to ensure that the options for the selected poll are retrieved and displayed correctly in the interface. The solution involved adjusting how server responses are handled and how the list of options is updated in the user interface.

3. **Updating the Poll List After Creating a New Poll:**
   When creating a new poll, the list of available polls for voting was not updated immediately; users had to reload the page to see the new poll in the list. This issue was resolved by implementing a dynamic update of the poll list after creating a new poll. Now, after creating a poll, the list of available polls updates automatically without the need to reload the page.

## Code Link

https://drive.google.com/drive/folders/1AvskIapLWcK09080HhqmshQApJKvvhey?usp=sharing

## Task Status

The task has been successfully completed. All identified issues have been resolved, and the required functionalities have been implemented correctly. The application allows for the creation of polls, voting on existing polls, and handles response messages appropriately.

There are no pending issues with this task. All requirements have been met, and the application has been verified to function correctly.

