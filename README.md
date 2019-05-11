# InterceptionJavaBinds
InterceptionJavaBinds is a binds for a Windows keyboard driver writen in Java (http://oblita.com/Interception).

Using the driver, Interceptor can simulate keystrokes and mouse clicks

# How to use
1. First of all you have to download 'interseption assets' from http://www.oblita.com/interception.html
2. Then install it. You can do it by running install-interception.exe with /install argument so it looks like 
```
install-interception.exe /install
```
3. Now download this repository and there you go.

Notes:
1. Dont forget to mark 'lib' directory as library cause programm uses property 'java.library.path'
2. If you want to use mouse methods you had to turn off mouse acceleration.
3. All methods tested on windows 10 with interceptor v.1.0.1.
4. There is also some documentation and you can generate java doc.
