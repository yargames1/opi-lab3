Технически все готово для запуска, но

тесты пока для наброска, возможно их стоит дополнить или переделать

как работает гит тоже надо будет потестить

я сменил Jar на War, тк у нас веб приложение, и по идее так логичнее + иначе нужен костыль в виде main файла, которого нет

у на Mac музыка тоже должна запускаться, проверь на всякий, там он в билде оба варианта системы смотрит и запускает, если ок все
```xml
 <target name="music" depends="build">
        <echo message="Build finished successfully!"/>

        <!-- Windows -->
        <exec executable="cmd" osfamily="windows" spawn="true">
            <arg value="/c"/>
            <arg value="start"/>
            <arg value=""/>
            <arg value="${music.file}"/>
        </exec>

        <!-- macOS -->
        <exec executable="open" osfamily="mac" spawn="true">
            <arg value="${music.file}"/>
        </exec>
        
    </target>```