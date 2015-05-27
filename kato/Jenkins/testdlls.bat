SetLocal EnableDelayedExpansion
echo ----- DIR
dir /s *Tests.dll /b | find "release" /i | find "bin"
dir /s *Tests.dll /b | find "release" /i | find "bin" > temp.txt
echo ----- LIST
set L=1
for /F "delims=" %%a in (temp.txt) do (
  set list=!list! %%a
  set  /a  L=!L!+1
)
echo %list%
echo ----- 
mkdir Dynamic
cd Dynamic
"C:\Program Files (x86)\Microsoft Visual Studio 14.0\Common7\IDE\CommonExtensions\Microsoft\TestWindow\vstest.console.exe" %list% /logger:trx
echo -----
