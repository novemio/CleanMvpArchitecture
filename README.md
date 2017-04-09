## ANDROID
***
[![N|Solid](https://static.addtoany.com/images/blog/github-icon.png)](https://github.com/novemio)


### Setup git message template
```
 cd (path to project)/app
 git config commit.template config/.gitmessage

```

### How to commit
1. git fetch
2. git checkout development
3. git checkout -b local_dev
4. Now your working branch is local_dev, change code (implement some task, bug feature)  
5. git add . // Add Changed Files
6. git commit // Commit changed files and add message described in ".gitmessage" document (git message template)
7. git log // Verify that your commit is added
8. git checkout development
9. git pull //Pull new changes that maybe are made by other developer
10. git checkout local_dev
11. git rebase development
12. Resolve conflicts if there are any
13. After rebase is done, test the application and verify that your changes are there
14. Verify that you did NOT made any REGRESSION!!!
15. When you are sure that everything is fine, than
16. git push origin development
17. Than your code goes on code review.  
