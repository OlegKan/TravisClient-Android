# Project description
[![Build Status on Travis:](https://travis-ci.org/brave-warrior/TravisClient-Android.svg?branch=master)](https://travis-ci.org/brave-warrior/TravisClient-Android)

This project is Android client for Travis CI.

It uses [API](http://docs.travis-ci.com/api/#overview) from Travis CI for fetching all required information about repositories, build tasks and build history.
The app has the following features:
- user login to Travis CI and Travis CI Pro
- list of repositories
- build history (commits and pull requests)
- build log
- restart build
- cancel build
- search with history
- intent filter for opening build links

[![Travis Client on Google Play Store](/screenshots/google-play-badge.png)](https://play.google.com/store/apps/details?id=com.khmelenko.lab.travisclient)

![Repositories list](/screenshots/main_screen.png)   ![Build history](/screenshots/build_history.png)   ![Build Details](/screenshots/build_details.png)

# Open source libraries
- [Android appcompat v7](https://github.com/android/platform_frameworks_support/tree/master/v7/appcompat)
- [Retrofit](https://github.com/square/retrofit)
- [OkHttp](https://github.com/square/okhttp)
- [Gson](https://code.google.com/p/google-gson/)
- [Dagger 2](https://github.com/google/dagger)
- [Butter Knife](http://jakewharton.github.io/butterknife/)
- [Event Bus](https://github.com/greenrobot/EventBus)
- [CircleImageView](https://github.com/hdodenhof/CircleImageView)
 
# Contribution
In case you have ideas or found an issue, don't hesitate to create pull request or an issue.

#License

[Apache Licence 2.0](http://www.apache.org/licenses/LICENSE-2.0)

Copyright 2015 Dmytro Khmelenko

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
