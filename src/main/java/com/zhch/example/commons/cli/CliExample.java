package com.zhch.example.commons.cli;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class CliExample {
	public void dateApp(String[] args) throws ParseException {
		System.out.println("DateApp====================================");
		// create Options object
		Options options = new Options();

		// 选项的名称|是否需要参数|显示的说明信息
		options.addOption("t", false, "display current time");

		// 这个选项需要参数, 如果命令行上没传入, 则值为null
		options.addOption("c", true, "country code");

		// 这个parser一般用来解析只有一个字母的选项, 像 -t, -n
		CommandLineParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);

		// hasOption判断是否有这个参数
		if (cmd.hasOption("t")) {
			// 打印日期和时间
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			System.out.println(format.format(new Date()));
		} else {
			// 只打印日期
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(format.format(new Date()));
		}

		String countryCode = cmd.getOptionValue("c");
		if (countryCode == null) {
			System.out.println("no param for c.");
		} else {
			System.out.println("c is : " + countryCode);
		}

	}

	/**
	 * 模拟ant的选项
	 */
	public void ant(String[] args) {
		
		// 期望输出
//		          ant [options] [target [target2 [target3] ...]]
//				  Options: 
//				  -help                  print this message
//				  -projecthelp           print project help information
//				  -version               print the version information and exit
//				  -quiet                 be extra quiet
//				  -verbose               be extra verbose
//				  -debug                 print debugging information
//				  -emacs                 produce logging information without adornments
//				  -logfile <file>        use given file for log
//				  -logger <classname>    the class which is to perform logging
//				  -listener <classname>  add an instance of class as a project listener
//				  -buildfile <file>      use given buildfile
//				  -D<property>=<value>   use value for given property
//				  -find <file>           search for buildfile towards the root of the
//				                         filesystem and use it
		
		System.out.println("Ant==============================");

		// boolean 值的选项
		Option help = new Option("help", "print this message");
		Option projecthelp = new Option("projecthelp", "print project help information");
		Option version = new Option("version", "print the version information and exit");
		Option quiet = new Option("quiet", "be extra quiet");
		Option verbose = new Option("verbose", "be extra verbose");
		Option debug = new Option("debug", "print debugging information");
		Option emacs = new Option("emacs", "produce logging information without adornments");

		// 带参数的选项
		Option logfile = OptionBuilder.withArgName("file").hasArg().withDescription("use given file for log")
				.create("logfile");

		Option logger = OptionBuilder.withArgName("classname").hasArg()
				.withDescription("the class which it to perform " + "logging").create("logger");

		Option listener = OptionBuilder.withArgName("classname").hasArg()
				.withDescription("add an instance of class as " + "a project listener").create("listener");

		Option buildfile = OptionBuilder.withArgName("file").hasArg().withDescription("use given buildfile")
				.create("buildfile");

		Option find = OptionBuilder.withArgName("file").hasArg()
				.withDescription("search for buildfile towards the " + "root of the filesystem and use it")
				.create("find");
		
		// java 属性形式的选项
		Option property  = OptionBuilder.withArgName( "property=value" )
                .hasArgs(2)
                .withValueSeparator()
                .withDescription( "use value for given property" )
                .create( "D" );
		
		Options options = new Options();

		options.addOption( help );
		options.addOption( projecthelp );
		options.addOption( version );
		options.addOption( quiet );
		options.addOption( verbose );
		options.addOption( debug );
		options.addOption( emacs );
		options.addOption( logfile );
		options.addOption( logger );
		options.addOption( listener );
		options.addOption( buildfile );
		options.addOption( find );
		options.addOption( property );
		
		
		// create the parser, GnuParser 可以接受多于一个字母的选项
	    CommandLineParser parser = new GnuParser();
	    CommandLine line = null;
	    try {
	        // parse the command line arguments
	        line = parser.parse( options, args );
	        
	        // 判断是否有参数, 取参数的值
	        if( line.hasOption( "buildfile" ) ) {
	        	// initialise the member variable
	        	String buildfileName = line.getOptionValue( "buildfile" );
	        }
	    }catch( ParseException exp ) {
	        // oops, something went wrong
	        System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
	    }
	    
	    // 产生帮助信息
	    HelpFormatter formatter = new HelpFormatter();
	    if(line.hasOption("help")){
	    	formatter.printHelp( "ant", options );
	    	
	    	// 同时打印用法
	    	formatter.printHelp( "ant", options, true);
	    }
	    
	}
	
	
	/**
	 * 模拟 ls 的选项
	 */
	public void ls(){
		
//		Usage: ls [OPTION]... [FILE]...
//		List information about the FILEs (the current directory by default).
//		Sort entries alphabetically if none of -cftuSUX nor --sort.
//
//		-a, --all                  do not hide entries starting with .
//		-A, --almost-all           do not list implied . and ..
//		-b, --escape               print octal escapes for nongraphic characters
//		    --block-size=SIZE      use SIZE-byte blocks
//		-B, --ignore-backups       do not list implied entries ending with ~
//		-c                         with -lt: sort by, and show, ctime (time of last
//		                           modification of file status information)
//		                           with -l: show ctime and sort by name
//		                           otherwise: sort by ctime
//		-C                         list entries by columns
		
		System.out.println("ls==============================");
		// create the command line parser
		CommandLineParser parser = new PosixParser();

		// create the Options
		Options options = new Options();
		
		// 这两个参数传哪个都是一样的
		options.addOption( "a", "all", false, "do not hide entries starting with ." );
		options.addOption( "A", "almost-all", false, "do not list implied . and .." );
		options.addOption( "b", "escape", false, "print octal escapes for nongraphic "
		                                         + "characters" );
		options.addOption( OptionBuilder.withLongOpt( "block-size" )
		                                .withDescription( "use SIZE-byte blocks" )
		                                .hasArg()
		                                .withArgName("SIZE")
		                                .create() );
		options.addOption( "B", "ignore-backups", false, "do not list implied entried "
		                                                 + "ending with ~");
		options.addOption( "c", false, "with -lt: sort by, and show, ctime (time of last " 
		                               + "modification of file status information) with "
		                               + "-l:show ctime and sort by name otherwise: sort "
		                               + "by ctime" );
		options.addOption( "C", false, "list entries by columns" );

		String[] args = new String[]{ "--al" };

		try {
		    // parse the command line arguments
		    CommandLine line = parser.parse( options, args );

		    // validate that block-size has been set
		    if( line.hasOption( "block-size" ) ) {
		        // print the value of block-size
		        System.out.println("block-size :" + line.getOptionValue( "block-size" ) );
		    }
		    // validate that block-size has been set
		    if( line.hasOption( "all" ) ) {
		    	// print the value of block-size
		    	System.out.println("all :"  );
		    }
		}
		catch( ParseException exp ) {
		    System.out.println( "Unexpected exception:" + exp.getMessage() );
		}
	}

	public static void main(String[] args){
		CliExample t = new CliExample();
		try {
			t.dateApp(args);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		t.ant(args);
		t.ls();
	}
}
