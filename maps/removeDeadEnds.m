function map = removeDeadEnds(map,mazes,passages)

for j= 1:length(mazes);
    mz = mazes{j};
    
    hasDeadEnds = 1;
    while hasDeadEnds
        
        doneClipping = 1;
        if ~isempty(mz)
            remove = [];
            for k=1:size(mz,1)
%                 if k==6 && j==5
%                 j
% end
                % check if there is more than point adjacent to current hallway
                % if not its a dead end remove it
                numAdjacent = getNumAdj(map,mz(k,1),mz(k,2));
                
                if numAdjacent < 2
                    doneClipping = 0;
                    map(mz(k,2),mz(k,1)) = 0;
                    remove = [remove; k];
                end
                
            end
            
            mz(remove,:) = [];
        end
        
        
        
        %         close all
        %         hold on
        %         spy(map)
        %         plot(mz(:,2),mz(:,1),'ro')
        
        if doneClipping
            hasDeadEnds = 0;
        end
        
    end
    
end

% remove bogus doors
for l = 1:size(passages,1)
    x = passages(l,1);
    y = passages(l,2);
    
    if getNumAdj(map,x,y)<2
        
        map(y,x) = 0;
        
    end
    
end

