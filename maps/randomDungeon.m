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


function [map,rooms,pts] = randomDungeon(map,rooms,turnBias)


group = size(rooms,1)+1;

% initialize first maze
[x,y] = openPoint(map);
oldD = getDir(map,x,y,[0 0],turnBias);
map(y,x) = group;

% assume first point is open
pts{1} = [x y 1];

mazeNo = 1;
pointsRemaining = 1;
while pointsRemaining
    group,x,y
    
    % check if we can move in current direction
    if isempty(oldD)
        done = 1;
    else
        done = 0;
    end
    while ~done
        
        newD = getDir(map,x,y,oldD,turnBias);
        if ~isempty(newD)
            % take step
            x = x+newD(1);
            y = y+newD(2);
            oldD = newD;
            
            if y==2 && x==12
                10
            end
            
            % add new point to map and pts
            map(y,x) = group;
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
    map(y,x) = group;
    
    % assume first point is open
    pts{mazeNo} = [x y 1];
    
end
