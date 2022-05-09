# Charles Translator
Android app of LINDAT translation service\
For now just translate Czech <-> Ukrainian, but if future will be more languages

## Architecture and technologies
* Base architecture is MVVM with Android viewmodel components
* UI is based on Android Jetpack Compose
* For DB is used Android Room
* For api communication is used Ktor library 
* For DI is used Hilt

## Localization
App contains hardcoded texts in English, Czech, Slovak and Ukrainian languages.\
Progress of tranlations:\
[![Crowdin](https://badges.crowdin.net/charles-translator/localized.svg)](https://crowdin.com/project/charles-translator)
<br />
If you want add your language to app, please contant u4u@ufal.mff.cuni.cz. We use https://crowdin.com/ for localization management.

## Maintainers
This project is mantained by:
* [Tomáš Krabač](https://github.com/krabatom)