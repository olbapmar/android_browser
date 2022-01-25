# Android mobile browser using webview

### By: Pablo Pastor Martín

This is my first android app

## The challenge

This is a basic web browser UI that supports navigating the web on a mobile device and built on top of Android’s built-in WebView component. It has to provide a URL entry field, back, forward and reload buttons as well as a way to switch tabs.

Additionally, as the user browses around the web, the URL entry field should be updated to reflect the URL of the currently loaded web page.

## How to build and run

In order to build and run the project, simply import the project in Android Studio and press the run button on any Android Virtual Device that you might have or proceed to create one if necessary. **Note**: Please keep inmind that `minSdk` is 21.

## Further improvements

This section contains improvements I would do in the future:

- Improve the look and feel of the app by adding animations on user actions and supporting gestures (swipe up to reload, etc)
- Allow users to customize the default URL via config menu
- URLs suggestions
- Default search engine for whenever the users introduces a string that is not a url
- Tests  (I would have loved to make tests, but I hadn't time to learn about them :( )