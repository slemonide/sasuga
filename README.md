# SaSuGa
Sandbox survival game with hyperdimensional manifolds,
variable gravity and artificial life.

## How to Use
Run `src/Main.java`.

## Keyboard Controls
* W,A,S,D, Arrow Keys - move
* 1,2,3,...,9,0 - choose inventory slot
* ENTER / RIGHT MOUSE KEY - place cellParallelepiped
* L / LEFT MOUSE KEY - remove cellParallelepiped
* SPACE BAR - pause rendering
* R - restart the game

## Current Progress
* It runs.
* Estimated V0.1 release date - May 10th.

## Documentation
* [Wiki](https://github.com/slemonide/sasuga/wiki)
* [javadoc](https://slemonide.github.io/sasuga/)
* [another javadoc](docs/index.html)


## Q/A
Q: What is the difference between Cell, Box and Parallelepiped?

A:
 * Cell - an abstract entity in the world
 * Box - a Spatial representing Cell in the graphical engine
 * Parallelepiped - an abstract entity representing a merged set of Box'es
 that form a parallelepiped; exists as a part of an optimization for the
 rendering process
