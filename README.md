<div align="center">
📇 Profile Hub

An offline-first Android app for finding users and managing contacts

Show Image Show Image Show Image Show Image Show Image

Show Image Show Image Show Image Show Image

</div>
📖 About

Users create accounts, configure their profiles, find other users, view their details and add them to contacts.

The local database is the source of truth. The app works without a connection, and WorkManager synchronizes local data with the remote server in the background.

Created as a pet project during the Android mentoring course at School++.

<!-- Add screenshots or a short demo GIF here: --> <!-- <p align="center"><img src="docs/screenshots.png" width="800"></p> -->
✨ Features
🔐 Authentication: sign up, sign in and auto sign-in
👤 Profile: create and edit your own profile
🔎 Users: search among all users and view their details
📒 Contacts:
add users to contacts
view contact details
search among contacts
delete a contact, delete several at once (multi-select) or by swipe
🔔 Push notifications
📴 Offline-first: data stays available without a network and is synced later by WorkManager
🛠 Tech Stack
Area	Technologies
Language	Show Image
UI	Show Image Show Image Show Image Show Image
Navigation	Show Image
Architecture	Show Image Show Image Show Image
DI	Show Image
Networking	Show Image
Storage	Show Image Show Image
Async	Show Image Show Image
Background	Show Image Show Image
Tools	Show Image Show Image
🏗 Architecture

The project follows Clean Architecture with an MVI presentation layer:

Presentation: Fragments render a single UI state and send user intents to the ViewModel
Domain: use cases and repository interfaces, independent from Android and data sources
Data: repository implementations that combine the remote API (Retrofit) and the local database (Room)
<details> <summary><b>Offline-first synchronization</b></summary>
The UI always reads data from the local Room database.
User changes are saved locally first.
WorkManager runs the synchronization between the local database and the remote server.
</details>

📂 Project Structure
app/
├── data/           # API, database, repository implementations
├── domain/         # models, use cases, repository interfaces
└── presentation/   # Fragments, ViewModels, MVI state and intents
