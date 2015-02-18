% function [mazeMap,mazeGroups] = randomMaze(map,groups)
void
numRoomPuts = 50;

roomScale = 7;
roomMin = 3;

m = 25;
n = 25;

[map,groups] = randomRoom([m,n],roomScale,roomMin,numRoomPuts);


% check for open space on map
spy(map)

