function newDir = newBreak(map,x,y)

m = size(map,1);
n = size(map,2);

dirs = [1 0; 0 1; -1 0; 0 -1];

possibleDirs = [];
for j=1:4
    
    d = dirs(j,:);
    
    xd = x+d(1);
    yd = y+d(2);
    
    % not open if its off the map
    if xd>m || xd<1 ||yd>n || yd<1
        continue
    end
    
    % check to make sure its not a filled space
    if map(xd,yd) ~=0
        continue
    end
    
    if numOpenDirs(map,xd,yd,d) ==3
       possibleDirs = [possibleDirs; d]; 
    end
    
end

numDirs = size(possibleDirs,1);
if numDirs>0
   newDir = possibleDirs(randi(numDirs,1),:); 
else
    newDir = [];
end
        