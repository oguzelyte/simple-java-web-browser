# Simple Java Web Browser
Simple URL loading browser capable of reading or setting up a homepage (if no file had been set up before), refreshing a page or bookmarking it.

It is capable of navigating through pages or loading a homepage.

It also has a separate menu for bookmarks and history, in which the user can view his persistent history and bookmarked pages, load them up and delete them.

# Usage

* Open the web browser, on which it asks you to provide a loadable homepage URL if the one provided in the configuration file is faulty, the file is empty or non-existent.
* If you choose not to do it, it sets www.google.co.uk as the default homepage. Onwards you are free to click on hyperlinks, the refresh or home button or the favourites button marked with a star. It will save the bookmarks and history and you can view those by pressing on the “bookmarks” and “history” buttons situated on the menu that is movable to wherever it is most convenient to you. When you click on one of those buttons, a panel appears allowing you to delete a selected URL or delete them all, or, rather, you can load one up once you click twice on it. If you have one of those panels opened and keep adding either favourite pages or navigating through pages, they will refresh and the panels will update the information for you.
* It is important to point out that when you choose to enter a custom URL onto the URL text field, it must start with **http://** or **https://**. The browser is perfectly responsive and works well unless you tamper with the “History.txt” file and create a bunch of gibberish that cannot be loaded as a URL.

