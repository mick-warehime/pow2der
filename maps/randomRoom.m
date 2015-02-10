% Place a bunch of random non-overlapping rooms.
% Fill in the remaining solid regions with mazes.
% Connect each of the mazes and rooms to their neighbors, with a chance to add some extra connections.
% Remove all of the dead ends.
function [map,groups] = randomRoom(mapDim,roomScale,roomMin,numRoomPuts)


map = zeros(mapDim);
groups = cell(numRoomPuts,1);
group = 1;
for j=1:numRoomPuts
    
    % random room top left
    x = randi(mapDim(1))+1;
    y = randi(mapDim(2))+1;
    
    % random height / width
    wid = randi(roomScale)+roomMin;
    hei = randi(roomScale)+roomMin;
    
    % make sure room wont go off map
    if x+wid+1>mapDim(1)
        continue
    end
    if y+hei+1>mapDim(2)
        continue
    end
    
    
    % check if there is a room nearby
    safe = 1;
    for k = 1:wid
        for l = 1:hei
            for m = -1:1
                for n = -1:1
                    
                    if map(x+k+m,y+l+n) > 0
                        safe = 0;
                    end
                end
            end
        end
    end
    if ~safe
        continue
    end
        
    % if we have a safe location fill in the room on the map and add it to
    % the groups list
    for k = 1:wid
        for l = 1:hei
            map(x+k,y+l) = group;
            groups{group} = [groups{group}; x+k y+l];
        end
    end
    group = group+1;
    
end

groups(group:end)=[];




