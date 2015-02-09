% function refreshRooms(dim,m,branch,frac_rooms,numran)
void

m = 200;
n=200;
r_min = 25.;
num_room=20;
branch = .2;
room_frac = .25;
rand_connect = 10;
screenSize = [640 480];
map_scale = screenSize(1)/max([ m n]);
numran = 10;
%  def refreshRooms(self,dim,m,branch,frac_rooms,numran):
%
%         dim=dim
%
% #       subroom parameters
sub_frac=0.6;
sub_cdf=[.5, .85, 1.];
r_close = 6*(r_min*r_min);
%
% #       random initial point and direction
% #        xo=num.array([(dim-10)*random.random_sample(2)+10])

% start near a corner of the room

xo=randsample([.25 .75 .25 .75],2).*[m n];
phi_rand=2*pi*rand(1);
%
% #        network stores room centers, path stores room connections
network(1,:) = round(xo);
path=[];
%
% #        path length counters
totalpath=1;
%
% #        loop condition
connected=1;
%
% #        to make the first path longer we turn down the branching prob
branch_prob=branch/3;
first_branch = 1;
%
%

% first point


while connected<num_room
    %     #            new point
    %     #    Pick a random displacement
    phi_new=rand*pi/6+phi_rand;
    rho_new=rand*r_min/4+r_min;
    
    
    %
    %     #    Set new point as old with random displacement, but not off the map
    
    xn = xo+rho_new*[cos(phi_new) sin(phi_new)];
    
    tol = 5;
    if xn(1) < tol; xn(1) = tol; end
    if xn(2) < tol; xn(2) = tol; end
    if xn(1) > m-tol; xn(1) = m-tol; end
    if xn(2) > n-tol; xn(2) = n-tol; end
    
    
    %
    %     #       Find distances of new point from other points in network
    min_dist = zeros(size(network,1),1);
    for j=1:size(network,1)
        min_dist(j) = sqrt(sum(network(j,:)-xn).^2);
    end
    %
    %     #       If it's not too close, add it to network
    
    if min_dist>r_min
        xo=xn;
        network=[network;round(xn)];
        connected=connected+1;
        totalpath=totalpath+1;
        
        %     #       Branch with some probability to a previous point in network
        if branch_prob>rand && size(network,1)>1
            %
            if first_branch
                branch_prob=branch;
                first_branch=0;
            end
            % #                randomly find point in network and add it again as new starting point
            ind = randi(connected);
            xo = network(ind,:);
            network = [network;round(xo)];
            
            % increase path and number of rooms in network
            connected=connected+1;
            
            path=[path;totalpath];
            totalpath=totalpath+1;
        end
        
    else
        phi_rand=2*pi*rand;
    end
    
end
%             if path_shape
%                 if path[-1]<m:
%                     path=num.append(path,m)
%                 end
%             else
%                 path=num.array([m])
%                 end
%
%
%     %         # Add random connections between points not in adjacent paths
%             randoms = [];
%     %
%             for jj = 1:rand_connect
% %                 # Pick a random point in the network
%                 rand_in = randi(num_room);
%     %
%     %             #Find its corresponding path and exclude points in that path
%                 kk = num.nonzero(path>rand_in)[0][0]
%     %
%                 if (kk==0):
%                     ignore_list = num.array(range(0,path[0]))
%                 else:
%                     ignore_list = num.array(range(path[kk-1],path[kk]))
%     %
%     %             # set x_1 as the coordinates of the the random point and x_2
%     %             # as points not on the same path as x_1
%                 x_1 = network[rand_in,:]
%                 keep_list = num.setdiff1d( num.array(range(m)),ignore_list)
%                 x_2 = network[keep_list,:]
%     %
%     %             # Find points on x_2 not equal to x_1
%                 not_x11 = num.array(x_2[:,0] != x_1[0])
%                 not_x12 = num.array(x_2[:,1] != x_1[1])
%                 not_x1 = num.nonzero(not_x11+not_x12)
%     %
%     % #            catch the case if no points are near x_1 and skip this loop iter
%                 if size(not_x1,1)<1
%                     continue
%                 end
%     %
%                 x_2 = x_2[not_x1,:][0]
%     %
%                 tlen = x_2.size/2
%     %             #Find the distances between x_1 and x_2
%                 x_1=num.array(num.kron(ones((tlen,1)),x_1))
%                 ran_dist = num.sum( (x_1-x_2)**2,axis=1)
%     %
%     %             #Find points on x_2 close to x_1
%                 close_points = num.nonzero(ran_dist< r_close)
%                 x_2 = x_2[close_points,:][0]
%     %
%                 if (x_2.size>0):
%                     num_close_points = x_2.size/2
%     %
%                     xi = num.array([x_1[0]])
%                     xii= num.array([x_2[ random.randint(num_close_points),:]])
%     %
%                     randoms = num.append(randoms,xi,axis=0)
%                     randoms = num.append(randoms,xii,axis=0)
%     %
%     % #        randoms was initiliazed as (0,0) now remove this point
%             randoms = randoms[1:,:]
%     %
%     % #        number of random connections = num_points/2
%             num_rand=randoms.shape[0]/2
%     %
%             network = num.append(network,randoms,axis=0)
%     %
%     % #        take care of path with addition of random room connections
%             append = 2*num.arange(1,num_rand+1)
%             path = num.append(path,path[-1]+append)
%     %
%     %         # determine which points are rooms using the ones of room_in and append to network
%             room_in=num.array([random.random_sample(network.shape[0])<sub_frac]).transpose()
%             network=num.append(network,room_in,axis=1)
%     %
%             room_index=num.nonzero(network[:,2])
%             room_net=network[room_index,:2][0]
%     %
%             m=network.shape[0]
%             while room_net.shape[0]>0:
% #            intial room
%             xo = room_net[0,:]
%             x_o=num.array([num.append(xo,1)])
%
% #            figure out number of subrooms
%             num_sub = 1 + num.nonzero(sub_cdf > random.random_sample() )[0][0]
%
%             for jj in range(num_sub):
% #                pick random search angle
%                 phi_rand=2*pi*random.random_sample()
%
% #                pick new point
%                 #    Pick a random displacement
%                 phi_new=random.normal(loc=0,scale=pi/6)+phi_rand
%                 rho_new=random.normal(loc=r_min, scale=r_min/4)
%                 #    Set new point as old with random displacement, but not off the map
%                 xn=num.array(xo+rho_new*num.array([num.cos(phi_new),num.sin(phi_new)]))
%                 xn=xn.clip(min=10, max=dim-10)
%                 #       Find distances of new point from other points in network
%                 temp=num.array(num.kron(ones((m,1)),xn))
%                 temp1 = num.array(num.sum((temp-network[:,:2])**2,axis=1))
%                 min_dist=num.amin(temp1,axis=0)
%
%                 x_n=num.round(num.array([num.append(xn,2)]))
%
%                 if min_dist>(sub_frac*r_min)**2:
%                     network=num.append(network,x_o,axis=0)
%                     network=num.append(network,x_n,axis=0)
%                     path = num.append(path, path[-1]+2)
%                     m=m+2
%
%             # Find points on room_net equal to xo
%             not_xo1 = num.array(room_net[:,0] != x_o[0,0])
%             not_xo2 = num.array(room_net[:,1] != x_o[0,1])
%             not_xo = num.nonzero(not_xo1+not_xo2)
%             room_net = room_net[not_xo,:][0]
%
% #        use 3 in third column to denote initial position of retard ghost
% #        maybe we should use a better method than the first room
% #        maybe have the branching prob be lower for first branch to get longer first chain
% #        which would have first room be on average farther away from other rooms
% #
%         init_ind=num.nonzero(network[:,2] == 1 )[0][0]
%         network[init_ind,2]=3
%         network,path = network,path
% #    def getPath(self, type, xi, xf):

