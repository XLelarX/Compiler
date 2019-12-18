package compiler.lelar.compiler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class Runner {
        private final static String PATH = "/classes/";
//    private final static String PATH = "/Users/Lelar/Desktop/JavaProjects/compiler/";
    private final static String EXECUTION_ERRORS = "Execution errors: ";
    private final static String COMPILE_ERRORS = "Compile errors: ";
    private final static String CREATE_ERRORS = "Create errors: ";


    private CompilerEntity run(String className, String code, String vars) throws InterruptedException, IOException {
        String folderName;
        List<String> mkdirErr;
        Process process;

        do {
            folderName = className + Math.round(Math.random() * 100);
            process = new ProcessBuilder("mkdir", PATH + folderName).start();
            mkdirErr = reader(process.getErrorStream());
        }
        while (!mkdirErr.isEmpty() && mkdirErr.get(0).equals("mkdir: " + PATH + folderName + ": File exists"));

        File newFile = new File(PATH + folderName + "/" + className + ".java");

        if (!newFile.exists()) {//TODO Допилить проверку при существовании файла
            newFile.createNewFile();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        fileOutputStream.write(code.getBytes());
        fileOutputStream.close();

//        Process process = new ProcessBuilder("javac", PATH + className + ".java").start();
        process = new ProcessBuilder("javac", PATH + folderName + "/" + className + ".java").start();
        Thread.sleep(1000);

        InputStream stderr = process.getErrorStream();
        if (stderr.available() == 0) {

//            process = new ProcessBuilder("java", "-cp", PATH, className).start();
            process = new ProcessBuilder("java", "-cp", PATH + folderName + "/", className).start();
            OutputStream stdin = process.getOutputStream();
            InputStream stdout = process.getInputStream();
            stderr = process.getErrorStream();

            writer(vars, stdin);

            List<String> errList = reader(stderr);
            if (!errList.isEmpty())
                errList.add(0, EXECUTION_ERRORS);

            CompilerEntity outEntity = new CompilerEntity(reader(stdout), errList);

//            newFile.delete();
//            new File(PATH + className + ".class").delete();
            Thread.sleep(1000);
            new ProcessBuilder("rm", "-R", PATH + folderName).start();

            return outEntity;
        } else {
            List<String> errList = reader(stderr);
            errList.add(0, COMPILE_ERRORS);

            CompilerEntity outEntity = new CompilerEntity(null, errList);

//            newFile.delete();
            Thread.sleep(1000);
            new ProcessBuilder("rm", "-R", PATH + folderName).start();
            return outEntity;
        }
    }

    String getClassName(String code) throws IOException {
        StringBuilder className = new StringBuilder();
        int indexOfStartClassName = code.indexOf("class ") + 5;//TODO если текст пустой

        if (indexOfStartClassName == 4)
            throw new IOException(CREATE_ERRORS + "\n   Code textarea is empty");

        while (code.charAt(indexOfStartClassName) == ' ')
            indexOfStartClassName++;

        while ((code.charAt(indexOfStartClassName) != '{') && (code.charAt(indexOfStartClassName) != '\r') &&
                (code.charAt(indexOfStartClassName) != '\n') && (code.charAt(indexOfStartClassName) != ' ')) {
            className.append(code.charAt(indexOfStartClassName));
            indexOfStartClassName++;
        }
        return className.toString();
    }

    private void writer(String vars, OutputStream outputStream) throws IOException {
        //TODO Добавить разделение переменных
        outputStream.write(vars.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    private List<String> reader(InputStream inputStream) throws IOException {
        String line;
        List<String> out = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = reader.readLine()) != null)
            out.add(line);

        reader.close();
        return out;
    }

    CompilerEntity start(String className, String code, String vars) throws Exception {
        ExecutorService service = Executors.newSingleThreadExecutor();

        final CompilerEntity[] compilerEntity = {new CompilerEntity()};
        try {
            Runnable r = () -> {
                try {
                    compilerEntity[0] = Runner.this.run(className, code, vars);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            };

            Future<?> f = service.submit(r);

            f.get(25, TimeUnit.SECONDS);     // attempt the task for two minutes
        } catch (final InterruptedException | TimeoutException | ExecutionException e) {
            throw new Exception(EXECUTION_ERRORS + '\n' + e);
        } finally {
            service.shutdown();
        }
        return compilerEntity[0];
    }
}

