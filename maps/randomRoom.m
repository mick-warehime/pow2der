% Place a bunch of random non-overlapping rooms.
% Fill in the remaining solid regions with mazes.
% Connect each of the mazes and rooms to their neighbors, with a chance to add some extra connections.
% Remove all of the dead ends.
function [map,rooms] = randomRoom(width,height,roomMin,roomMax,numRoomPuts)

% make sure roomMax/Min are odd
if mod(roomMax,2)==0
    roomMax = roomMax+1;
end
if mod(roomMin,2)==0
    roomMin = roomMin+1;
end

% set the possible top left x/y values only even values possible
xVals = 2:2:width;
yVals = 2:2:height;

% set possible roomSizes
roomVals = roomMin:2:roomMax;

map = zeros(height,width);
groups = cell(numRoomPuts,1);
group = 1;
rooms = [];
for j=1:numRoomPuts
    
    % random room top left
    x = randsample(xVals,1);
    y = randsample(yVals,1);
    
    % random height / width
    w = randsample(roomVals,1);
    h = randsample(roomVals,1);
    
    % make sure room wont go off map
    if x+w>width
        continue
    end
    if y+h>height
        continue
    end
    
    
    % check if is overlapping aroom
    safe = 1;
    for k = 0:(w-1)
        for l = 0:(h-1)
            
            if map(y+l,x+k) > 0
                safe = 0;
            end
            
        end
    end

    if ~safe
        continue
    end
    
    % if we have a safe location fill in the room on the map and add it to
    % the groups list
    for k = 0:(w-1)
        for l = 0:(h-1)
            map(y+l,x+k) = group;                     
        end
    end
    
    rooms = [rooms; x y w h];
    group = group+1;
    
end

groups(group:end)=[];
