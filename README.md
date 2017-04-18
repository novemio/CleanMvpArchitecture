## ANDROID
***
[![N|Solid](https://static.addtoany.com/images/blog/github-icon.png)](https://github.com/novemio)


### Setup git message template
```
 cd (path to project)
 git config commit.template config/.gitmessage

```

### How to commit
Follow the concept:
 http://jeffkreeftmeijer.com/2010/why-arent-you-using-git-flow/
 https://github.com/nvie/gitflow

### Run tests
1. Junit tests: ```./gradlew test ```
2. Instrumented tests:  ```./gradlew connectedAndroidTest ```