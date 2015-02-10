function dirs = randomDir(map,x,y,do)

m = size(map,1);
n = size(map,2);

dirs = [1 0; 0 1; -1 0; 0 -1];

% dont look back the way you came
in = ismember(dirs,-do,'rows');
dirs = dirs(~in,:);


remove = [];
for k=1:length(dirs)
    
    
    tempD = dirs(k,:);
    
    % make sure direction points to location in map
    if x+tempD(1)>1 && x+tempD(1)<=m
        continue
    end
    if y+tempD(2)>1 && y+tempD(2)<=n
        continue
    end
    
    remove = [remove; k];
    
end

dirs(remove,:) = [];