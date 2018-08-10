package bitpal;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;


import java.math.BigInteger;

/**
 * 使用gnu.getopt 处理命令行参数,
 *
 *  <dependency>
 *      <groupId>gnu.getopt</groupId>
 *      <artifactId>java-getopt</artifactId>
 *      <version>1.0.13</version>
 *  </dependency>
 *
 * Created by zhangjikai on 16-3-14.
 */
public class GnuGetOpt {


    public static void printHelp() {
        StringBuilder builder = new StringBuilder();
        builder.append("Options:\n\n");
        builder.append(String.format("%-30s", "-M,--match <arg>:"));
        builder.append("Specify the match score which should be positive or zero. Default is 2.\n\n");
        builder.append(String.format("%-30s", "-I,--mismatch <arg>: "));
        builder.append("Specify the mismatch score which should be negative. Default is -3.\n\n");
        builder.append(String.format("%-30s", "-G,--gap <arg>: "));
        builder.append("Specify the gap score which should be negative. Default is -5.\n\n");
        builder.append(String.format("%-30s", "-d,--directory <arg>: "));
        builder.append("Specify the directory where to place generated source files.\n\n");
        builder.append(String.format("%-30s", "-h,--help: "));
        builder.append("Display help Information.\n\n");
        builder.append(String.format("%-30s", "-n,--name <arg>: "));
        builder.append("Set the name of generated source file.\n\n");
        builder.append(String.format("%-30s", "-t,--type <arg>: "));
        builder.append("Set the type of generated files. Valid values are: cpu, mic, avx2. Default is cpu.\n\n");
        System.out.println(builder.toString());
    }

    public static void handleArgs(String[] args) {
        LongOpt longOpts[] = new LongOpt[7];
        longOpts[0] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');
        longOpts[1] = new LongOpt("directory", LongOpt.REQUIRED_ARGUMENT, null, 'd');
        longOpts[2] = new LongOpt("name", LongOpt.REQUIRED_ARGUMENT, null, 'n');
        longOpts[3] = new LongOpt("match", LongOpt.REQUIRED_ARGUMENT, null, 'M');
        longOpts[4] = new LongOpt("mismatch", LongOpt.REQUIRED_ARGUMENT, null, 'I');
        longOpts[5] = new LongOpt("gap", LongOpt.REQUIRED_ARGUMENT, null, 'G');
        longOpts[6] = new LongOpt("type", LongOpt.REQUIRED_ARGUMENT, null, 't');

        int c;
        int num;
        String arg;
        Getopt opt = new Getopt("bitpal", args, "M:I:G:d:hn:t", longOpts);

        while ((c = opt.getopt()) != -1) {
            switch (c) {
                case 'M':
                    arg = opt.getOptarg();
                    try {
                        num = Integer.parseInt(arg);
                    } catch (Exception e) {
                        System.err.println("-M: You should input an integer.");
                        System.exit(-1);
                    }
                    break;

                case 'I':
                    arg = opt.getOptarg();
                    try {
                        num = Integer.parseInt(arg);
                    } catch (Exception e) {
                        System.err.println("-I: You should input an integer.");
                        System.exit(-1);
                    }
                    break;

                case 'G':
                    arg = opt.getOptarg();
                    try {
                        num = Integer.parseInt(arg);
                    } catch (Exception e) {
                        System.err.println("-G: You should input an integer.");
                        System.exit(-1);
                    }
                    break;

                case 'd':
                    arg = opt.getOptarg();
                    break;
                case 'h':
                    printHelp();
                    break;
                case 'n':
                    arg = opt.getOptarg();
                    break;
                case 't':
                    arg = opt.getOptarg();
                    if (!"cpumicavx2".contains(arg)) {
                        System.err.println("-t: Valid values are {cpu, mic, avx2}");
                        System.exit(-1);
                    }
                    break;
                case '?':
                    System.err.println("The option '" + (char) opt.getOptopt() + "' is not valid");
                    System.exit(-1);
                    break;
                default:
                    break;
            }
        }
    }

    public static void main(String[] args) {

        handleArgs(args);

    }
}
