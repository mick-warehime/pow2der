function newDir = getDir(map,x,y,oldD,turnBias)

% turnBias value 0-1
% larger values --> more bends in path
% smaller values --> less bends in path
% 0 --> induces sprirals


% possible search directions excluding looking backwards
dirs = [0 1; 0 -1; 1 0; -1 0];
newDirs = setdiff(dirs,-oldD,'rows');

width = size(map,2);
height = size(map,1);

% new direction is clear
possibleDirs = [];
for j=1:size(newDirs,1)
    d = newDirs(j,:);
    
    % take a temp step in d direction
    xd = x+d(1);
    yd = y+d(2);
    if xd>(width-1) || xd<2 ||yd>(height-1) || yd<2
        continue
    end
    % if we are turning only turn on even value tiles
    if sum(d==oldD)~=2
        
        
        dirChange = find(d);
        
        if dirChange == 1
            % 1 = turning to go up or down (y must be even)
            if mod(yd,2) == 1
                continue
            end
        elseif dirChange == 2
            % 2 = turning to go left or right (x must be even)
            if mod(xd,2)==1
                continue
            end
        end
        
    end
    if map(yd,xd) == 0
        if testOpen(map,xd,yd,map(y,x))
            possibleDirs = [possibleDirs; d];
        end
    end
    
end

numDirs = size(possibleDirs,1);
if numDirs>0
    % bias the previous direction (straighter paths)
    if ismember(oldD,possibleDirs,'rows') && rand>turnBias;
        newDir = oldD;
    else
        newDir = possibleDirs(randi(numDirs,1),:);
    end
else
    newDir = [];
end






