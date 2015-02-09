% function final_rooms = paramRoom(npts)

%
% #        preallocate vector of final points
final_rooms = [];
%
room_points = zeros(2,npts);
%
        counter =0;
%
%
        rooms = network[num.nonzero(network[:,2])[0],:]
%
%         first_loop = 1
%
%         while rooms.shape[0]>0:
%
% #            get room center
%             xi = rooms[0,:2]
%
% #            number of parameterization sine waves
%             cav=zeros((3,2),dtype=float)
%
%
% #            base magnitude of room edge path parameterizations
%             if rooms[0,2]==1 or rooms[0,2]==3:
%                 rr = 2.*random.random_sample()
%                 rmag = [rr*(ii+.5) for ii in range(1,3)]
%                 its = len(rmag)
%             if rooms[0,2]==2:
%                 rmag=[1]
%                 its = 1
%
%             phase=[0,0]
%             num_vecs=3
%             for ll in range(its):
%
%                 cmag = 2.5*rmag[ll]*(random.random_sample(2)+.5)
%
%     #            define random vectors for path parameterization
%                 for kk in range(2):
%                     mag = 1+random.random_sample()*rmag[ll]**2
%                     temp = num.array([random.random_sample(2)])
%                     cav[kk,:]=mag*temp/la.norm(temp)
%                     phase[kk] = random.random_sample()*2*pi
%
%     #            parameterize with three sine functinos in three directions and
%     #            store points
%
%                 room_points[0,:] = xi[0] + cmag[0]*num.cos(2*pi*room_vec) + cav[0,0]*num.sin(6*pi*room_vec+phase[0])+cav[1,0]*num.sin(8*pi*room_vec+phase[1])
%                 room_points[1,:] = xi[1] + cmag[1]*num.sin(2*pi*room_vec) + cav[0,1]*num.sin(6*pi*room_vec+phase[0])+cav[1,1]*num.sin(8*pi*room_vec++phase[1])
%
%                 room_points = num.round(room_points)
%
%     #            keep a list of the paths
%                 if first_loop:
%                     final_rooms=room_points
%                     first_loop=0
%                 else:
%                     final_rooms=num.append(final_rooms,room_points,axis=1)
%
%
% #                remove current and all copies from matrix rooms
%                 no_x = num.array(rooms[:,0] != xi[0])
%                 no_y = num.array(rooms[:,1] != xi[1])
%                 no_more_xy = num.nonzero(no_x + no_y)
%
%                 try:
%                     if len(no_more_xy)>0:
%                         rooms=rooms[no_more_xy,:][0]
%                 except:
%                     print 'rooms = ', rooms
%                     print 'no_more_xy = ', no_more_xy
%
%         room_points = final_rooms.clip(min=10,max=dim-10)
%
%         return num.int_(final_rooms)
%
%     def drawPath(self,vec):
%
% ##        set all points in the matrix in vec to 1
%         for jj in range(vec.size/2):
%             matrix[vec[0,jj],vec[1,jj]]=1
%
% #        plot.show()
% #        matrix=num.matrix(eye(50,dtype=int))
% #        dim=50
%
%         temp=matrix.copy()
%         m=10
%         n=dim-10
%
%         for jj in range(m,n):
%             for ll in range(-2,3,1):
%
%                 for kk in range(-2,3,1):
%                     matrix[jj,m:n] += temp[jj-ll,(m+kk):(n+kk)]
%
%         matrix=matrix.clip(max=1)
%         ###
%         matrix = 1- matrix
%
%         ###
%     ###
%
%     def process_matrix(self):
%
%         m = dim
%         n = dim
%
%         neighbors = [ (1,1),(1,0),(1,-1),(0,-1),(-1,-1),(-1,0),(-1,1),(0,1)]
%         for ii in range(1,m-1):
%             for jj in range(1,n-1):
%                 if matrix[ii,jj]==1:
%                     matrix[ii,jj] = -1
%                     for kk in range(8): #Check whether a neighbor is walkable==0.
%                         if matrix[neighbors[kk][0]+ii,neighbors[kk][1]+jj]==0:
%                             matrix[ii,jj] = 1
%                             break
%
%         matrix[:,0] = -1
%         matrix[:,n-1] = -1
%         matrix[0,:] = -1
%         matrix[m-1,:] = -1
% #        #    use matplotlib to show lvl.matrix
% #        plot.matshow(matrix)
% #        plot.show()
%
%
%     def start_pt(self): #Return the starting positing of the wizard
%         return [network[0,0],network[0,1]]
%
%
%     def nav_map(self):
%
%             #Initialize map fog surface
%             map_fog = pygame.Surface((dim*map_scale,dim*map_scale), HWSURFACE|HWPALETTE,8)
%             map_fog.set_palette([[0,0,0],[0,0,0]])
%             map_fog.fill(0)
%     #        set invisible color in color pallette anything colored '1' will be invisible on top of the background
%             map_fog.set_colorkey(1, RLEACCEL)
%
%
%     #        initialize nav_map surface
%             map_surf = pygame.Surface((dim*map_scale,dim*map_scale), HWSURFACE|HWPALETTE,8)
%             map_surf.set_palette([[65,48,29],[0,0,0],[133,120,105]])
%             #2 is wall, 1 is background , 0 is walkable
%             map_surf.fill(2)
% #            map_surf.set_alpha(255)
%
%     #        make 2d array of the pixels in map_surf
%             arr=surfarray.pixels2d(map_surf)
%
%             matrix_ones = num.array(num.nonzero(matrix==1))[:,0,:]
%             matrix_mones = num.array(num.nonzero(matrix==-1))[:,0,:]
%
%     #        the scale arrays define how to draw the map at various multiples of the original size
%             scale_array = num.arange(map_scale)
%
%             scale_x = num.append(scale_array,scale_array)
%
%             for j in range(map_scale-1):
%                 scale_x = num.append(scale_x,scale_array)
%
%             scale_y = num.sort(scale_x)
%
%
%     #        copy matrix into the surface array
%             for jj in range(matrix_ones[0].size):
%
%                 arr[matrix_ones[1,jj]*map_scale+scale_x, matrix_ones[0,jj]*map_scale+scale_y] = 0
%
%             #        copy matrix into the surface array
%             for jj in range(matrix_mones[0].size):
%
%                 arr[matrix_mones[1,jj]*map_scale+scale_x, matrix_mones[0,jj]*map_scale+scale_y] = 1
%
%     #        set the nav map to the center of the main screen
%             map_pos = [screensize[0]/2-dim*map_scale/2,screensize[1]/2-dim*map_scale/2]
%
%     def radarUpdate(self,screen,pos,color_index):
%
% #        input parameters
% #        screen - surface to blit radar onto... the map
% #        pos - list of tuples of x,y positions
% #        color_index -  same length as pos determines color of each radar blip
%
%
% #        list of possible color choices for radar blips
%
%         color_list = [(20,34,41),(10,150,10),(10,10,150),(0,0,0)]
%
%         if radar_counter<10:
%             width = 0
%         else:
%             width = 21-(radar_counter+1)
%         if radar_counter>19:
%             radar_counter = 0
%
%
%         for jj in range(len(color_index)):
%             pygame.draw.circle(screen,color_list[color_index[jj]],pos[jj],radar_counter,width)
%
%         radar_counter += 1
%
%
% #    Update the fog surface covering unknown regions of the map
%     def mapFogUpdate(self,vis_mat):
%
%
%
%         matrix_ones = num.array(num.nonzero(vis_mat==1))[:,:]
%
%         arr_fog = surfarray.pixels2d(map_fog)
%
% #        the scale arrays define how to draw the map at various multiples of the original size
%         scale_array = num.arange(map_scale)
%
%         scale_x = num.append(scale_array,scale_array)
%
%         for j in range(map_scale-1):
%             scale_x = num.append(scale_x,scale_array)
%
%         scale_y = num.sort(scale_x)
%
%     #        copy vis_mat into the fog_surface array
%         for jj in range(matrix_ones[0].size):
%
%             arr_fog[matrix_ones[1,jj]*map_scale+scale_x, matrix_ones[0,jj]*map_scale+scale_y] = 1
