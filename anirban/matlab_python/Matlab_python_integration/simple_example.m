%
%   simple example to show the usage of l1_ls
%

% problem data
function [result] = simple_example(A, y)
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
 