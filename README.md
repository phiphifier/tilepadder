# tilepadder
CLI tool to easily add and remove padding from tilesets

INSTALLATION **LINUX ONLY**
To install, make install.sh runnable if it isn't already, then run it.
After installation, you should have access to the "tilepadder" command in your terminal.



USAGE
To use tilepadder, the syntax is the following:

tilepadder [PATH TO TILESET] [TILE SIZE] [PADDING AMOUNT]

Set padding amount as a positive number to add padding to a tileset without any
or
use a negative number to remove padding from a tileset that already has some



HELP
If you need to see usage in the future via terminal, type
"tilepadder -h"
or
"tilepadder --help"
or
simply run tilepadder with no arguments.



USAGE EXAMPLES
Add two pixels of padding:
tilepadder /home/phiphifier/Desktop/tileset.png 32 2

Remove two pixels of padding
tilepadder /home/phiphifier/Desktop/tileset.png 32 -2



MISC
Feel free use, modify, and/or redistribute this code as you please. If you want to modify the logic, the uncompiled source code can be found on my github @ https://www.github.com/phiphifier/tilepadder
