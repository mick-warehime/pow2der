

numRoomPuts = 50;

roomScale = 7;
roomMin = 3;

m = 50;
n = 60;

[map,groups] = randomRoom([m,n],roomScale,roomMin,numRoomPuts);

map= map>0;


spy(map)