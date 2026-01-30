# tileset padder/extruder
CLI tool to easily add and remove padding to/from tilesets  
This tool adds padded borders by extruding the edge pixels, preventing flickering or gaps (seams) between tiles in game engines.

**Java 17 or newer is required!**

INSTALLATION **LINUX ONLY** (recommended)  
To install on Linux, download the "tilepadder linux installer.zip" file, extract it, make install.sh runnable if it isn't already, then run it.
After installation, you should have access to the "tilepadder" command in your terminal.



TO USE ON ANY OS  
To use on any device, download "TilePadder.java", and place the file where you want. Let's use your desktop for an example.
To run it, the usage is similar to below, but replace `tilepadder` with `java [PATH TO TILE PADDER JAVA FILE]`.
For example, on Windows if you wanted to add two pixels to a tileset located on your desktop, the command might look something like this:

`java C:\\Users\[your name]\Desktop\TilePadder.java C:\\Users\[your name]\Desktop\tileset.png 32 2`



USAGE  
To use tilepadder, the syntax is the following:

`tilepadder [PATH TO TILESET] [TILE SIZE] [PADDING AMOUNT]`

Set padding amount as a positive number to add padding to a tileset without any
or
use a negative number to remove padding from a tileset that already has some



HELP  
If you need to see usage in the future via terminal, type
`tilepadder -h`
or
`tilepadder --help`
or
simply run `tilepadder` with no arguments.



USAGE EXAMPLES  
Add two pixels of padding:
`tilepadder /home/phiphifier/Desktop/tileset.png 32 2`

Remove two pixels of padding
`tilepadder /home/phiphifier/Desktop/tileset.png 32 -2`



MISC  
Feel free to use, modify, and/or redistribute this code as you please. If you want to modify the logic, the uncompiled source code can be found on my github at
https://www.github.com/phiphifier/tilepadder (TilePadder.java)
