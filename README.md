# Pokemon app

This app is made as part of in interview process

## How to run

Works in Android Studio Flamingo

## Approach

I decided to store the list of species in a DB, so we can filter on name.
(Duplicating data to a local DB is acceptable here because the data doesn't change ofter, for very
transient data it is not advisable to store it locally).
For a production app I would expect to query the API whether a sync is needed.

Stack used: Kotlin, MVVM, Room, Retrofit, Navigation, RxJava, Koin

I split the project into modules:

- the app module is as light weight as possible,
- there are 3 modules for the "species" feature, roughly as clean architecture suggests, but I
  don't use "use cases", so the domain layer only has the entities and the repository interface.
- at the moment there is just one core module for the theming, but if there were more features or
  the features become more complicated I would create more core modules for common functionality.

Most business logic is under unit test, but I didn't try to get to 100% coverage for this
assignment.
I've created tests for one view model, the repository, and deserilization of the API response,
so you can see how I would approach testing, but it's not complete.

There is some error handling that you can test by turning off the internet connection. Just keep
in mind that the species list comes from the DB, so to test errors on that screen you'd have to
clean storage or uninstall the app first.
