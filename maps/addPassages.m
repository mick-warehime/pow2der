function [map,passages,door] = addPassages(map,rooms,probBonusConnect,numBonusAttempts)
tempMap = map;

passages = [];
bonusPassages = [];

% when add a bonus connection grab from original list
locs0 = getConnectivity(tempMap);
numLocs0 = size(locs0,1);

% first group should be a room?
firstRoom = randi(size(rooms,1));

while length(unique(tempMap))>2
    
    % recalculate cxns
    [locs,cxn] = getConnectivity(tempMap);
    
    
    connected = [];
    % find all connections between connected groups
    posPassage = [];
    
    
    pasIn = find((cxn(:,1)==firstRoom |cxn(:,2)==firstRoom));
    posPassage = [posPassage; pasIn];
    
    
    
    % randomly selected one of those connections
    for j=1:(1+numBonusAttempts)
        getPassage = 0;
        
        if j==1
            getPassage = 1;
        else
            if ~isempty(find(posPassage,1))
                % every attempt after first, roll for another passage
                % added from original list to increase variation
                if rand<probBonusConnect
                    randLoc = randsample(1:numLocs0,1);
                    bonusPassages = [bonusPassages; locs0(randLoc,:)];
                end
            end
        end
        
        if getPassage
            in = randsample(posPassage,1);
            passages = [passages; locs(in,:)];
            connected = union(connected,cxn(in,:));
            
            % remove the attached passage
            posPassage(posPassage==in,:) = [];
        end
        
    end
    
    
    % consolodate connections
    for j=1:length(connected)
        tempMap(tempMap==connected(j)) = firstRoom;
    end
    
%     close all
%     hold on
%     spy(map)
%     plot(locs(:,1),locs(:,2),'ro')
%     plot(passages(:,1),passages(:,2),'ko','markerfacecolor','k')
%     
end


door = max(map(:))+1;
for j=1:size(passages,1) 
   map(passages(j,2),passages(j,1)) = door;
end
for j=1:size(bonusPassages,1) 
   map(bonusPassages(j,2),bonusPassages(j,1)) = door;
end



