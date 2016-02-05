#Use Cases

_US 01.01.01_
Name: 
	AddAThing
Actors:
	Logged-in user (primary)
	Server 
Goal:				
	Add a new thing to the user's list of things.
Precondition:
	User must be logged in.
	The add button on the items page must be clicked
		to open the add thing view.
	Item's information must be filled in in the item
		profile view.
Trigger:
	Click the 'Save' button on the item profile view.
Postcondition:
	The item has been added for the user as an available item.
	The item profile view is opened with editing disabled. 
Exceptions
	Information not filled in on the item profile view.
	Invalid information filled for item.


_US 01.01.02_
Name: 
	SeeAllThings
Actors:
	Logged-in user (primary)
	Server
Goal:
	See all of user's things. 
	Includes all things lent out, rented, and owned
		but not lent out.
Precondition:
	User must be logged in.
	User must be on home page.
Trigger:
	Click the 'Items' button on the home page.
Postcondition:
	The Items view is opened with the default view.
Exceptions


_US 01.01.03_
Name: 
	ViewMyThing
Actors:
	Logged-in user (primary)
	Server
Goal:
	See details of a specific thing that user has.
Precondition:
	User must be logged in.
	User must be on items page.
	User must own item.
Trigger:
	Click on an item in any of the tabs on the items page.
	The item status and owner determines what page opens.
Postcondition:
	Item profile is opened with 'Edit' button and a
		'View Bids' button.
Exceptions


_US 01.01.04_
Name: 
	EditMyThing
Actors:
	Logged-in user (primary)
	Server
Goal:
	Edit details of a specific thing that user has.
Precondition:
	User must be logged in.
	User must own item.
	Item must be in user's possession, i.e. it's status 
		must be available.
	User must click 'Edit' button on an item's profile page.
Trigger:
	Click on the 'Save' button in an item's profile.
Postcondition:
	The item is saved with its new information.
Exceptions


_US 01.01.05_
Name: 
	DeleteMyThing
Actors:
	Logged-in user (primary)
	Server
Goal:
	Delete a specific thing that user has.
Precondition:
	User must be logged in.
	User must own item.
	Item must be in user's possession, i.e. it's status 
		must be available.
	User must click 'Edit' button on an item's profile page.
Trigger:
	Click on the 'Delete' button in an item's profile.
Postcondition:
	The item is deleted from the system.
Exceptions


_US 02.01.01_
Name: 
	ThingHasStatus
Actors:
	Server (primary)
Goal:
	All items must have a status.
Precondition:
Trigger:
	Adding, bidding-on, and lending out items must change
		item's status.
Postcondition:
	Adding a new item will initialize it with available status.
	Bidding on an item will change status to bidded.
	Accepting a big will change status to borrowed.
Exceptions
