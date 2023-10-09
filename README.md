# Test Task For Heads And Hands

Telegram for communication: @Wizarlock

This is my solution to a test task for Heads and Hands. In this project I implemented a game system of entities such as Player and Monster using OOP. Below you can see the task

## The task

1) There are Creatures in the game. These include the Player and Monsters.

2) The Creature has Attack and Defense parameters. These are integers from 1 to 30.

3) The Creature has Health. This is a natural number from 0 to N. If Health becomes 0, then the Creature dies. The player can heal himself up to 4 times for 30% of his maximum Health.

4) The Creature has a Damage parameter. This is the range of natural numbers M - N. For example, 1-6.

5) One Creature can hit another according to the following algorithm:

   - We calculate the attack modifier. It is equal to the difference between the Attack of the attacker and the Defense of the defender plus 1
  
   - Success is determined by rolling N dice numbered 1 to 6, where N is the Attack Modifier. At least one die is always rolled.

   - The hit is considered successful if at least one of the dice rolls 5 or 6

   - If the strike is successful, then an arbitrary value is taken from the attacker’s Damage parameter and subtracted from the defender’s Health.
  
All entities must be written and designed in OOP style. Objects must respond to incorrect method arguments.

Your program must have the entity classes Player and Monster. Availability of additional classes at your request.

The result should be an application with an implementation of the classes and an example of their use.
