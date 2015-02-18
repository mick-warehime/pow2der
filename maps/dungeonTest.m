void

numRoomPuts = 500;

probBonusConnection = .5;
numBonusAttempts = 2;
turnBias = .6;
roomMax = 7;
roomMin = 3;

width = 21;
height = 81;

[map,rooms] = randomRoom(width,height,roomMin,roomMax,numRoomPuts);

[map,rooms,mazes] = randomDungeon(width,height,roomMin,roomMax,numRoomPuts,turnBias);

[map,passages,door] = addPassages(map,rooms,probBonusConnection,numBonusAttempts);

map = removeDeadEnds(map,mazes,passages);

% map = addWalls(map,rooms,door);

% convert to rooms, doors, and mazes
nRooms = size(rooms,1);
map(map<=nRooms & map>0) = 1; % room
map(map==door) = 3; % door
map(map>nRooms & map<door) = 2; % hall

map = expandHallways(map);



hold all
c = [{'ro'},{'bo'},{'ko'}];
for j=1:3
    [I,J] = find(map==j);
    
    plot(I,J,c{j})
    
end








% map = expandHallways(map,rooms,door);


