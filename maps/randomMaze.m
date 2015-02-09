% function [mazeMap,mazeGroups] = randomMaze(map,groups)
void
numRoomPuts = 50;

roomScale = 7;
roomMin = 3;

m = 5;
n = 5;

[map,groups] = randomRoom([m,n],roomScale,roomMin,numRoomPuts);
% check for open space on map


mazeMap = map;
mazeGroups = cell(1);
pts = [];
mazeGroup = 1;

group = length(groups)+1;

[xo,yo] = openPoint(map);
map(xo,yo) = group;
pts = [xo,yo,1];
stepDir = [1 0];
notDone = 1;
if ~isempty(xo)
    
%     while notDone
    for j=1:200
        
        % try to take a step from current location
        stepDir = mazeStep(map,xo,yo,stepDir);
        
        %
        %  currently checking all 9 nearby squares, should only check the
        %  three directions you need to, move right then check up down
        %  right etc...
        %
        
        % if you can step add the current point
        if ~isempty(step)
            
            % add open point and take step
            
            xo = xo+stepDir(1);
            yo = yo+stepDir(2);
            pts = [pts; xo yo 1];
            map(xo,yo)=group;
        else
%             pts(end,3)=0;
%             
%             % backtrack looking for an open point
%             for o = size(pts,1):-1:1
%                 if pts(o,3) == 1
%                     xo = pts(o,1);
%                     yo = pts(o,2);
%                 else
%                     closedPath = 1;
%                 end
%                 [pts; xo yo 0];
%             end
%             notDone = 0;
        end
        
        
        
    end
    
end

for j=1:size(pts,1)
    map(pts(j,1),pts(j,2))=group;
end


