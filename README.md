# Ribot Viewer (Java)

_**N.B.** This is a port of the Kotlin version._

This project is a chance for me to try out a lot of new things as well as going back to basics regarding
clean architecture. 

To build the project you will need to use the latest Android Studio in the beta channel.

The architecture components I have used are:

- Room Persistence Library
- ViewModel
- LiveData

Some other libraries that were used:

- RxJava2
- Dagger2
- Retrofit2
- Glide

Code has been separated between 3 modules; data, domain and app/presentation. 

## Discussion

Tests are missing due to this being a port of the kotlin version and I will add more tests to that
repo rather than this one.


Throughout development, the latest [Guide to App Architecture](https://developer.android.com/topic/libraries/architecture/guide.html)
article on the Android developer site was followed. I have been intrigued by this technology since
Google IO so thought I would give it a try and see how it worked.

Tweaks were made to use RxJava and some modifications based 
on use case such as the refresh trigger.

To begin with, the `ViewModel` was going to be connected to the `Activity` by subscribing the the `ViewModel`'s
flowables & observables. However, this soon became messy and it was not functioning as expected. This
 could have been due to my understanding of RxJava but once I could test again, it became apparent that
 the cleaner and more testable solution I could implement was to use a combination of RxJava and LiveData.
 
There is not any Ui testing due to time constraints but I would have used Espresso for this.

Any observations, improvements and suggestions are more than welcome!


