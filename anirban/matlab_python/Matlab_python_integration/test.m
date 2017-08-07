wholecontent = fileread('data/content_K10_60_round1.txt'); %read content from file
splitcontent = strsplit(wholecontent, '#');  %split content at each #
wholetag = fileread('data/tag_K10_60_round1.txt'); %read the tags from file
splittag = strsplit(wholetag, '#'); %split the tags at each #
actualSignal = dlmread('data/actualSignal_K10_60_round1.txt')'; %read the actual signal
avgerror = 0; %average error 
lambda = 1;      % regularization parameter
rel_tol = 0.01;     % relative target duality gap
parfor fidx = 1:numel(splitcontent)  %iterate over each section
    y = str2num(splitcontent{fidx}); %get the y value
    Phi = sparse(str2num(splittag{fidx})); %get sparse Phi matrix
    Y  = y';          % measurements with no noise
    [x,status]=l1_ls(Phi,Y,lambda,rel_tol, true); %call l1_ls code
    %ACCUMULATE AVERAGE ERROR ON ALL RUNS
    avgerror = avgerror+ sqrt(sum((actualSignal-x).^2)/ sum(actualSignal.^2));    
end
disp(avgerror/numel(splitcontent));
