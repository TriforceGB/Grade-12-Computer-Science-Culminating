# Git Guide
This guide is made to help the member that don't really know what they are doing with Git. This will go over the basics and also the few commands that are going to be used.
## Git Controls
### Basic Controls
#### Committing
The most important things you need understand is the workflow of commits. Becuase your using an IDE, most of this is handled for you in the **source control** menu. This menu allows you to view and manage your commits.

You can think of commits like snapshots or saves of your code, and before you want to upload and share your code, you first need to save it or commit it. Typically, you want to commit your changes regularly and with meaningful commit messages on what changes you made. In some cases it better off commiting one some files at a time to help better organize your commits, and find when certain changes were made. 

**However** to make your life easier, I would just suggest to at least make 1 commit per day or per feature you complete. As long as you commit you code and push (upload) it Github by the end of the day it should be all good.

Note that VScode has a Commit all button that you can press to just commit all changes you made at once.
#### Pushing
As mentioned earlier, you can think of Pushing like uploading your code to Github. VScode will prompt you to do this after you commit every change you made. Make sure to do this as **soon** as you commit your changes. so it just easiler for other to collaborate with you.

#### Pulling
Pulling is the opposite of pushing. It allows you to download and merge changes from a remote repository into your local repository. You can think of it like downloading and updating your code from Github. Typically, for this project unless your working on the same branch as someone else or use 2 computer to code, you won't be doing any typical pulling.

### More Advanced Controls
Most of these aren't possible with VS code but rather has to be done in Terminal or with Github UI or CLI
#### Branching
Branching allows you to create a separate version of your codebase. you can think of it as everyone make their own copy of the code and allows them to work byself without affecting the main codebase. for this codebase, each of us have our own branch that we can work on independently.

Then when you are done, you can merge those changes on your own version into the main codebase. 

People can also make branches of branches so people can make a branch of your stuff, make a change and then merge that into your own branch.

You can switch between branches using the following command:
```bash
git switch <branch>
```
Or use the button found in VS code in the bottom left corner.

#### Merging
Merging is the process that allows taking multiple branches and combining them into a single branch. Typically this can't be done in VS code but rather has to be done on GitHub or the command line. 

Merging allows us to add together the code we made separately into a single branch. It also like people make edit of what you currently have in a different branch and merge it in later into your own branch.

To merge you can either 

a) go onto GitHub and press the merge button. From there you tell GitHub which branch you want to merge into which branch.

b) Use the command line to merge. This can be done using the following command while inside the branch you want to merge into:
```bash
git merge <branch>
```
From there you would just Push the changes to GitHub.

##### Merge Conflicts
Note that when you Merge and some change you made conflicts with the changes in the branch you are merging into, you will need to resolve those conflicts before the merge can be completed. This can normally be done in Edtior where it will display 2 versions of the file side by side, with the conflicting changes highlighted. Just pick the one you want to keep and then commit the file after all changes are made.

##### Pull Request
A pull request is a way to propose changes to the main codebase. When you make a pull request, you are basically asking to merge your stuff into the main codebase. before this take place, GitHub will prompt you to review what changes you made and if you follow all the guidelines. From there I (Zach) will skim it and make sure everything looks good before merging it in.
##### Pulling from Main
Sometimes while working on your branch, an update to main might take place where you need those changes, rather the just pulling normally you need to *merge* those changes into your own branch. 

This can be done using this command:
```bash
git merge origin/main
```
this will attempt to merge the changes from the main branch into your own branch.
### Other Stuff
I can't be ask to write about this stuff but if you want to dive in deeper you can go learn some of these things
- Git Amend
- Rebasing
- Stashing
## Git Settings
There are also a few settings that you would want to configure to make your Git experience better. All of these are made in the Terminal and can be done using the following commands:
```bash
git config --global user.name "<NAME>"
git config --global user.email "<EMAIL>"
```
