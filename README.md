# ContactBook
Android Application written in Kotlin to sync contacts from server and let you browse contacts,edit them, or perform actions like call, email, message on yor contact, moreover you can favoroutie them to add them to your favorite list.
Works while you are online and or offline (not connected to internet).

# How to use the app :
The app works in both offline and online more. It sync data from cloud, and show list of contacts as well as store those contats to local db, now you can edit contacts, add pictures or add them to you favorite list, add new contacts( if offline only saves to DB / if online then sync the new contact to server).

** Since saving images is not supported by API, thus we are only able to store and load iamges from phone. To do this you can select and contact and edit it, on the edit screen you have the option to select iamge from your gallery for that contact.Once saved, the app can start showing an image next to that contact always.

# App Details :
This is a simple application for fetching and displaying Contact list. You can also add or edit contacts (works both online / offline ) .

# Technology Stack
   	Kotlin
    MVVM
    Coroutines
    Retrofit2
    Koin
	Android Architecture Components
 	 -DataBiniding
	 -LiveData
	 -Room
	Recycler View 
    Juni4 
    Mockito
 
	
# UI 
	Material Design Principles
	Supports Dark Mode and Light Mode 
# Reference
API :: https://drive.google.com/drive/folders/1LkifTrgfClsR13Ih-mbXZWNCCocIH-V5
UI  :: https://material.io/
# App Screenshots


# LightMode : 

![Contact List Screen](/images/contactlist.jpeg)

![Add New Contact](/images/addnewcontact.jpeg)

![Add New Contact Empty](/images/addnewcontactempty.jpeg)

![Contact Details ](/images/contactdetails.jpeg)

![Edit Contact ](/images/editcontact.jpeg)


# Future Goals : 
While this is just the MVP of the actual prodcut, given more time there are few advances which this app would need to give its users a flawless and memorable experience. 

1. Pull to Refresh -> The pupil list needs pull to refresh listview. This is specially important when the user was offline while he was using the app( he was being displayed the cached data from DB) but if he suddeenly comes online, currently user have to kill the app and reload the app to load new data from server. But with pull to refresh the user can be saved lots of trouble. 

2. offline data sync : We can provide a sync button that syncs all newly added contacts (which user added while being offlie) with server when he is next online.

3. Writing more test cases. 

4. Improve api for having property of image, unique id for each contact. 
