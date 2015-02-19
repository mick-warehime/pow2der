void

numRoomPuts = 500;

probBonusConnection = .5;
numBonusAttempts = 2;
turnBias = .6;
roomMax = 7;
roomMin = 3;

width = 21;
height = 31;

[map0,rooms] = randomRoom(width,height,roomMin,roomMax,numRoomPuts);

[map1,rooms,mazes] = randomDungeon(map0,rooms,turnBias);

[map2,passages,door] = addPassages(map1,rooms,probBonusConnection,numBonusAttempts);

map3 = removeDeadEnds(map2,mazes,passages);

% map = addWalls(map,rooms,door);

% convert to rooms, doors, and mazes
nRooms = size(rooms,1);
map3(map3<=nRooms & map3>0) = 1; % room
map3(map3==door) = 3; % door
map3(map3>nRooms & map3<door) = 2; % hall

map4 = expandHallways(map3);



hold all
c = [{'ro'},{'bo'},{'ko'}];
for j=1:3
    [I,J] = find(map4==j);
    
    plot(J,I,c{j})
    
end








% map = expandHallways(map,rooms,door);


