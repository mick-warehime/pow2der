function open = isOpen(map,x,y,do)

% all possible search directions
dirs = [1 0; 0 1; -1 0; 0 -1];

% only look in the following possible directions (max three directions)
in = ismember(dirs,-do,'rows');
dirs = dirs(~in,:);

width = size(map,2);
height = size(map,1);

open = 1;
for j = 1:size(dirs,1)
   
    if xn+k>1 && xn+k<=width
        for l = -1:1
            
            % make sure y is in the board
            if yn+l>1 && yn+l<=height
                
                if (xn+k) ~= x || (yn+l) ~= y
                    if map(yn+l,xn+k)~=0
                        open = 0;
                    end
                    
                end
            end
        end
    end
end