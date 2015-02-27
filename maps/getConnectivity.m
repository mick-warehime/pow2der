function [locs,cxns] = getConnectivity(map)

width = size(map,2);
height = size(map,1);

cxns = [];
locs = [];

% only odd rows can have north south connections
for x=3:2:(width-2)
    for y = 2:(height-1)
        %ignore 0's
        if map(y,x+1) ~=0 && map(y,x-1)~=0

            % if two points away is a nonzero point that is not the same
            % as the current point add connection
            if map(y,x+1)~=map(y,x-1)
                
                locs = [locs; x y]; 
                cxns = [cxns; map(y,x+1) map(y,x-1)];
                
            end            
        end        
    end
end


% get vertical connections
% only odd rows can have north south connections
for y=3:2:(height-2)
    for x = 2:(width-1)
        %ignore 0's
        if map(y+1,x) ~=0 && map(y-1,x)~=0

            % if two points away is a nonzero point that is not the same
            % as the current point add connection
            if map(y+1,x)~=map(y-1,x)
                
                locs = [locs; x y]; 
                cxns = [cxns; map(y+1,x) map(y-1,x)];
                                
            end            
        end        
    end
end
