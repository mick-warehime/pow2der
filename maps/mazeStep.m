function stepDir = mazeStep(map,x,y,do)

m = size(map,1);
n = size(map,2);

% random search directions
dirs = randomDir(map,x,y,do);
dirs = dirs(randperm(size(dirs,1)),:);

stepDir = [];
for k = 1:size(dirs,1)
    
    % get a random direction
    d = dirs(k,:);
    
    % make sure the step is not out of the map
    if x+d(1)>=1 && x+d(1)<=m && y+d(2)>=1 && y+d(2)<=n
        
        if map(x+d(1),y+d(2))==0
            % check if the step is open
            if isOpen(map,x,y,d)
                
                stepDir =d;
                break
                
            end
        end
    end
end