# ImagesRenamer

## 1. Description

ImagesRenamer is a very simple utility which aims to rename large quantity of images in a single directory.
New names are based on a template that include a prefix, determined by the user or by the name of the directory.
The limit of the number of images that can be renamed at once is theoretically limited at 9999,
but in practice, ImagesRenamer does not. See "How does it work ?" for more information.

While it's created for images, it can also theorically rename other files, regardless of their extension.
You should be aware of what you are doing with it, since there is not a revert feature.

> Note : This program is the result of a private commission, which explains some of the design choices.

## 2. How does it work ?

First of all, when run, ImagesRenamer will create a log file where the jar file is located.
Inside it, you'll find informations that I hope are useful and understandable enough.

Obviously, to begin with, you want to select the directory containing all the files to rename.
Once directory name and full path is retrieved, sub-directories are filtered so they won't be renamed.
Files will then be sorted according to their last modified date, with the older one coming in first place.

After this step, you'll be asked to either keep and use the directory name as the prefix or enter a new one.
At the moment, the prefix must meet those requirements :
	- Being 6 characters long
	- Containing only digits
	- Starting by '16'
When the prefix is validated, files are renamed according to the following template :
`16XXXX-YYYY` where 16XXXX is the prefix and YYYY a number from 0001 to 9999.

Once the renaming process is done, the program will wait until it's closed by the user.

## 3. Usage
### Prerequisites
You'll need Java version 17 or later.

### How to run
- Donwload a release
- Launch the jar file by :
	- Double-clicking on it
	- Opening it using console command : `java -jar ImagesRenamer-X.X.jar`
- Select a directory
- Confirm or change the prefix
- Wait while files are renamed according to the template
- You're done !

## 4. What I planned for next release

- Limit on files number
- Proper french and english languages implementation (because mixing both of them is just terrible)
- Fix label disparition when progress bar is working (if it drives me mad, I'll leave it as a feature)
- Cancel prefix validation revert back to directory selection
- Constraints system for prefix validation instead of hard-coded ones
- Option to change the template
- Prettier GUI (if I have time to kill)
