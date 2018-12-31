# RemoveMyWaste

![RemoveMyWaste icon](/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png)

# Abstract

Improper household hazardous waste removal can cause environmental pollution and physical injuries, having a negative impact on human health. The RemoveMyWaste app provides a solution to this issue by providing consumers with easier access to proper waste removal information and connecting them to local waste collection authorities if necessary for their waste to be safely removed.

# Installation

## Requirements:
Android device or emulator running Android version 4.0.4 (Ice Cream Sandwich, API Level 15) or above.    

## Download URL: 

- Go to to https://github.com/cs361-group24/RemoveMyWaste/releases
- Download `RemoveMyWaste.apk` from the latest release.

## To Install:
- Open the above file in your android device.
- Allow “Install unknown apps” permission if you trust the app will not harm your device.
- Install and run.

## To Uninstall:
- Open your device's `Settings`
- Select `App & Notifications`
- Select `RemoveMyWaste`
- Select `Uninstall`

# Contributing

Pull requests welcome!

## dev environment

The user-facing components of the app are developed in [android studio](https://developer.android.com/studio/).

The database that provides the hazardous materials data to the app may be found here ⇢ [database](https://github.com/cs361-group24/database).

### To clone this repo into android studio

- I'm in android studio: File > New > Project from Version Control > Git     
- I'm on the splash screen: Checkout project from Version Control
2) Enter **https://github.com/cs361-group24/RemoveMyWaste** into the url field. Click OK/Clone.

### To commit after changing files

1) VCS > Git > Commit (Ctrl+K)
2) Select the top-most option (In my version of android studio, it was "Unversioned Files").
3) Write a commit message. Click Commit.
4) There might be errors that you can optionally review.

### To push to the master branch after commit

1) VCS > Git > Push (Ctrl+Shift+K)

### Helpful Plugins:
- [floobits](https://floobits.com/help/plugins/intellij): Collaborative editing similar to google docs (except for code). Requires you to setup up an account (should be free).

## Roadmap

A wishlist of sorts:

- iOS version: This might involve porting form native Java to a cross-platform framework like React Native.
- Web version: a parallel version for desktop users.
- Request user's location, so that the nearest center's are returned.
- Add user profile with options to "Login/Logout" or "Continue as Guest".
- An option to "Update database" would be helpful. Or even the option to "Select database" should another one be made.
- ~~Switch from remote MySQL/MariaDB database to local SQLite: so users can read from the database without a network connection.~~ **Added in v0.2.0.**
- ~~Add map view.~~ **Added in v0.2.0.**
