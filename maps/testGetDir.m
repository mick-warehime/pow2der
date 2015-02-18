% test get dir

% make sure rooms are an odd distance away from all other rooms 
% make sure rooms are an odd didstance aawy from walls
% make sure rooms have an odd dimensions

%% notes about maze algorithm (low river = many short dead ends, long right = fewer longer deadends)

% currently taking most recent open cell done on line 39 of backtrack
% should incorporate randomness factor by finding all open elements and
% choosing with some frequency a random open point

% Growing tree algorithm: This is a general algorithm, capable of 
% creating Mazes of different textures. It requires storage up to the 
% size of the Maze. Each time you carve a cell, add that cell to a list. 
% Proceed by picking a cell from the list, and carving into an unmade cell 
% next to it. If there are no unmade cells next to the current cell, remove
% the current cell from the list. The Maze is done when the list becomes 
% empty. The interesting part that allows many possible textures is how 
% you pick a cell from the list. For example, if you always pick the most
% recent cell added to it, this algorithm turns into the recursive 
% backtracker. If you always pick cells at random, 
% this will behave similarly but not exactly to Prim's algorithm.
% If you always pick the oldest cells added to the list, this will
% create Mazes with about as low a "river" factor as possible, even lower
% than Prim's algorithm. If you usually pick the most recent cell, but 
% occasionally pick a random cell, the Maze will have a high "river"
% factor but a short direct solution. If you randomly pick among the most
% recent cells, the Maze will have a low "river" factor but a 
% long windy solution.

void

numRoomPuts = 250;

roomMax = 9;
roomMin = 3;

m = 31;
n = 51;

[map,groups] = randomRoom(m,n,roomMin,roomMax,numRoomPuts);

group = length(groups)+1;

% initialize first maze
[x,y] = openPoint(map);
oldD = getDir(map,x,y,[0 0]);
map(x,y) = group;

% assume first point is open
pts{1} = [x y 1];

mazeNo = 1;
pointsRemaining = 1;
while pointsRemaining
    
    % check if we can move in current direction
    if isempty(oldD)
        done = 1;
    else
        done = 0;
    end
    while ~done
        spy(map)
        drawnow
        newD = getDir(map,x,y,oldD);
        if ~isempty(newD)
            % take step
            x = x+newD(1);
            y = y+newD(2);
            oldD = newD;
            
            % add new point to map and pts
            map(x,y) = group;
            pts{mazeNo} = [pts{mazeNo}; x y 1];
            
        else
            [x,y,oldD,pts{mazeNo}] = backtrack(map,x,y,pts{mazeNo});
            % try to back track
            if isempty(x)
                done = 1;
            end
        end
        
    end
      
    % try to get a new point
    [x,y] = openPoint(map);
    
    % no points remain
    if isempty(x)
        pointsRemaining = 1;
        break
    end
    
    
    
    oldD = getDir(map,x,y,[0 0]);
    group = group+1;
    mazeNo = mazeNo+1;
    map(x,y) = group;
    
    % assume first point is open
    pts{mazeNo} = [x y 1];
    
end
