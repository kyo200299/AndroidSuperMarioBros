***********************************************************************************************
***************************************  Reminder Note  ***************************************

v1.1 is not the first version, the old one was deleted because of some error due to CVS that 
I did not know how to solve. The project was upload to a wrong folder, so I manually moved it.
Then it does not update to current version when it's committed.
After recreating projects several times I manually deleted the project folder, but later found
out a way to solve it, which is deleting CVS settings for the project folder by right clicking 
on the project select Team->Disconnect...




***********************************************************************************************
****************************************  Version Log  ****************************************
***********************************************************************************************

v1.1 - Everything works fine, except question block does not pop. So Mario transformation has 
       not been enabled.

v1.2 - Everything works correct, any updated version after this might be adding features or
       level designing.
       
v1.3 - Making enemies a little easier to kill, GoldenMario does not transform back to BigMario 
       when using mushroom, only receiving score.
       
       
       
       
***********************************************************************************************
*************************************  User Instructions  *************************************
***********************************************************************************************
 ________________________________________
|                   |                    |		( A ) : Jump
|                   |                    |			
|                   |                    |		( B ) : Throw axe for AxeMario(Not Enabled)
|       Left        |        Right       |
|                   |                    |		Left & Right: Move or hold-down on touch screen
|                   |                    |		              to move Mario to left or right
|                   |                    |
|___________________|____________________|
|      ( B )        |        ( A )       |
|___________________|____________________|

Levels are controlled by main menu, press level button to select level and press back button on
device to go back to main menu.
Mario can kill enemies by landing on top of them, but GoldenMario is immune to cannon.

Game starts with SmallMario. Mario transforms according to the item used:

	Green Mushroom	:	Life plus one
	Red Mushroom	:	BigMario - transforming back to SmallMario when attacked by an enemy
	Star			:	GoldenMario - immune to cannon ball, transform to Bigmario by others
	



***********************************************************************************************
***********************************************************************************************