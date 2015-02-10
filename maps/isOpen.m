function open = isOpen(map,x,y,do)

% all possible search directions
dirs = [1 0; 0 1; -1 0; 0 -1];

% only look in the following possible directions (max three directions)
in = ismember(dirs,-do,'rows');
dirs = dirs(~in,:);

m = size(map,1);
n = size(map,2);

open = 1;
for j = 1:size(dirs,1)
    do
    if xn+k>1 && xn+k<=m
        for l = -1:1
            
            % make sure y is in the board
            if yn+l>1 && yn+l<=n
                
                if (xn+k) ~= x || (yn+l) ~= y
                    if map(xn+k,yn+l)~=0
                        open = 0;
                    end
                    
                end
            end
        end
    end
end