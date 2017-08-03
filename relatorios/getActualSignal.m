%
%   returns the actual signal after taking in 
%   Phi matrix and Y by use of the optimisation of l1_ls
%

% problem data
function [result] = getActualSignal(A, y)
  Phi  = A
  %x0 = [1 0 1 0]';    % original signal
  %x0 = m';
  Y  = y';          % measurements with no noise
  lambda = 0.01;      % regularization parameter
  rel_tol = 0.01;     % relative target duality gap

  [x,status]=l1_ls(Phi,Y,lambda,rel_tol);
  fprintf('The recovered value of X0 is :\n')
  disp(x)
  result = x;
 end 
 