function count = numOpenDirs(map,x,y,dirs)

if nargin<4
    dirs = [1 0; 0 1; -1 0; 0 -1];
end

m = size(map,1);
n = size(map,2);



count= 0;
for k = 1:size(dirs,1)
    
    d = dirs(k,:);
    
    % its clear if its off the map
    if x+d(1)>m || x+d(1)<1 ||y+d(2)>n || y+d(2)<1
        
        count = count+1;
        continue
    end
    
    % if there is no tile there increment count
    if map(x+d(1),y+d(2))==0
        count = count + 1;
    else
        break
    end
    
end