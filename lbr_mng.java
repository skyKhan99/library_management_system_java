package libary_management_system;
import javax.swing.*;
import java.awt.*; 
import javax.swing.border.TitledBorder;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
public class lbr_mng {
	
	static File admnLoginInfo = new File("admin_login_info.txt");
	private static JTextField tfMemberNumber;
	private static JTextField tfBookNumber;
	private static JTextField tfLoanedBooks;
	private static JTextField tfBooksAtLibrary;
	private static JTextField textFieldBookName;
	private static JTextField textFieldAuthorName;
	private static JTextField textFieldMemberName;
	private static JTextField textFieldSurname;

	
	public static void main(String[] args) {
		Image appLogo = Toolkit.getDefaultToolkit().getImage("logo.jpg");
//_________Main Frame Creating..._____________________________________________________________________________________________________________________________
		JFrame fr = new JFrame();
		fr.setResizable(false);
		fr.setIconImage(appLogo);
		fr.setTitle("Library Management System");
		fr.setSize(400,440);
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.getContentPane().setLayout(null);
//_______________Admin Panel__________________________________________________________________________________________________________________________________
		JButton btnAdminLogin = new JButton("ADMIN LOGIN");
		btnAdminLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdminLogin.setFont(new Font("Carlito", Font.BOLD, 20));
		btnAdminLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FileReader admnLoginInfoFr = new FileReader(admnLoginInfo);
					String adminId = JOptionPane.showInputDialog("ID:", null); //request id from user
					BufferedReader adminLoginInfoBr = new BufferedReader(admnLoginInfoFr);
					String getAdmLgnInfo = adminLoginInfoBr.readLine(); // read database, so 'txt' file :)
					String[] parseAdmLgnInfo = getAdmLgnInfo.split(";");
					
					//id input check
					if(parseAdmLgnInfo[0].equals(adminId)) {
						String adminPassword = JOptionPane.showInputDialog("Password:", null);
						
						//password input check
						if(parseAdmLgnInfo[1].equals(adminPassword)) {
							fr.setVisible(false);
							
							JFrame adminPanelFrame = new JFrame();
							adminPanelFrame.setVisible(true);
							adminPanelFrame.setTitle("Library Management System - Admin Panel");
							adminPanelFrame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							adminPanelFrame.setSize(745,600);
							adminPanelFrame.getContentPane().setLayout(null);
							adminPanelFrame.setIconImage(appLogo);
							adminPanelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							
							File librarianList = new File("librarian_list.txt");
							FileReader librarianListFr = new FileReader(librarianList);
							BufferedReader librarianListBr = new BufferedReader(librarianListFr);
							
							DefaultListModel<String> libList = new DefaultListModel();
							JList listView = new JList(libList);
							
							JButton btnNewButton = new JButton("New Librarian");
							btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							btnNewButton.setFont(new Font("Constantia", Font.BOLD, 20));
							btnNewButton.setBounds(10, 10, 711, 60);
							btnNewButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									try {
										createNewLibrarian(librarianList, librarianListBr, librarianListFr, libList);
										listView.setModel(libList);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
							});
							adminPanelFrame.getContentPane().add(btnNewButton);
							
							JLabel lblNewLabel = new JLabel("Librarian List");
							lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							lblNewLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
							lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
							lblNewLabel.setBounds(10, 91, 140, 41);
							adminPanelFrame.getContentPane().add(lblNewLabel);
							
							
							if(!librarianList.exists()) {
								librarianList.createNewFile();
							}
							
							FileReader libListFr = new FileReader(librarianList);
							@SuppressWarnings("resource")
							BufferedReader libListReader = new BufferedReader(libListFr);
							String x = libListReader.readLine();
							System.out.println(x);

							
							if(x != null) {
								String[] librarians = x.split(";");
								for(int i = 0; i < librarians.length; i++) {
								String[] librariansDetail = librarians[i].split(",");
								libList.addElement(librariansDetail[0] + "\sPW->\s" + librariansDetail[1]);
								}
							}							
							
							JScrollPane listScrollPane = new JScrollPane();
							listScrollPane.setBounds(10, 150, 271, 391);
							adminPanelFrame.getContentPane().add(listScrollPane);
							
							listScrollPane.setViewportView(listView);
							listView.setVisibleRowCount(15);
							listView.setLayoutOrientation(JList.VERTICAL_WRAP);
							listView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							
							JButton btnDeleteLibrarian = new JButton("Delete Librarian");
							btnDeleteLibrarian.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									deleteLibrarian(listView, libList, librarianList);
								}
							});
							btnDeleteLibrarian.setFont(new Font("Constantia", Font.BOLD, 11));
							btnDeleteLibrarian.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							btnDeleteLibrarian.setBounds(156, 82, 125, 25);
							adminPanelFrame.getContentPane().add(btnDeleteLibrarian);
							
							JButton btnEditLibrarian = new JButton("Edit Librarian");
							btnEditLibrarian.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									editLibrarian(listView, libList, librarianList);
								}
							});
							btnEditLibrarian.setFont(new Font("Constantia", Font.BOLD, 11));
							btnEditLibrarian.setBounds(156, 117, 125, 25);
							adminPanelFrame.getContentPane().add(btnEditLibrarian);
							
							JButton btnChangeLoginParameter = new JButton("Change Login Parameters");
							btnChangeLoginParameter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							btnChangeLoginParameter.setFont(new Font("Constantia", Font.BOLD, 20));
							btnChangeLoginParameter.setBounds(309, 82, 412, 60);
							btnChangeLoginParameter.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									String approveAdmId = JOptionPane.showInputDialog("Old Id:");
									if(!approveAdmId.equals(parseAdmLgnInfo[0])) { 
										JOptionPane.showMessageDialog(fr, "Please enter true ID.", "Wrong ID", JOptionPane.WARNING_MESSAGE);
										}
									else {
										String approveAdmPass = JOptionPane.showInputDialog("Old Password:");
										if(!approveAdmPass.equals(parseAdmLgnInfo[1])) { 
											JOptionPane.showMessageDialog(fr, "Please enter true password.", "Wrong Password", JOptionPane.WARNING_MESSAGE);
											}
										else {
											String setNewID = JOptionPane.showInputDialog("Set new ID:");
											String setNewPass = JOptionPane.showInputDialog("Set new Password:");
											try {
												FileWriter admDbWriter = new FileWriter(admnLoginInfo);
												admDbWriter.write(setNewID + ";" + setNewPass);
												admDbWriter.close();
												JOptionPane.showMessageDialog(fr, "New login infos saved successfully.", "Saved", JOptionPane.INFORMATION_MESSAGE);
											} catch (IOException e1) {
												JOptionPane.showMessageDialog(fr, "Unexpected error has been.", "ERROR", JOptionPane.ERROR_MESSAGE);
												e1.printStackTrace();
											}
										}
									}
								}
							});
							adminPanelFrame.getContentPane().add(btnChangeLoginParameter);
							
							JLabel lblNumberMember = new JLabel("Number of Members:");
							lblNumberMember.setBounds(299, 185, 170, 16);
							adminPanelFrame.getContentPane().add(lblNumberMember);
							
							JLabel lblNumberBook = new JLabel("Number of Books:");
							lblNumberBook.setBounds(299, 213, 170, 16);
							adminPanelFrame.getContentPane().add(lblNumberBook);
							
							JLabel lblLoanedBook = new JLabel("Number of Loaned Books:");
							lblLoanedBook.setBounds(299, 241, 170, 16);
							adminPanelFrame.getContentPane().add(lblLoanedBook);
							
							JLabel lblBookAtLibrary = new JLabel("Number of Books at Library:");
							lblBookAtLibrary.setBounds(299, 269, 170, 16);
							adminPanelFrame.getContentPane().add(lblBookAtLibrary);
							
							tfMemberNumber = new JTextField();
							tfMemberNumber.setBackground(new Color(224, 255, 255));
							tfMemberNumber.setEditable(false);
							tfMemberNumber.setBounds(487, 183, 170, 20);
							adminPanelFrame.getContentPane().add(tfMemberNumber);
							tfMemberNumber.setColumns(10);
							
							String memberNumber = String.valueOf(adminPanelGetMemberNumber());
							tfMemberNumber.setText(memberNumber);
							
							tfBookNumber = new JTextField();
							tfBookNumber.setEditable(false);
							tfBookNumber.setColumns(10);
							tfBookNumber.setBackground(new Color(224, 255, 255));
							tfBookNumber.setBounds(487, 211, 170, 20);
							adminPanelFrame.getContentPane().add(tfBookNumber);
							
							String bookNumber = String.valueOf(adminPanelGetBookNumber());
							tfBookNumber.setText(bookNumber);
							
							tfLoanedBooks = new JTextField();
							tfLoanedBooks.setEditable(false);
							tfLoanedBooks.setColumns(10);
							tfLoanedBooks.setBackground(new Color(224, 255, 255));
							tfLoanedBooks.setBounds(487, 239, 170, 20);
							adminPanelFrame.getContentPane().add(tfLoanedBooks);
							
							String loanedNumber = String.valueOf(adminPanelGetLoanedBookNumber());
							tfLoanedBooks.setText(loanedNumber);
							
							tfBooksAtLibrary = new JTextField();
							tfBooksAtLibrary.setEditable(false);
							tfBooksAtLibrary.setColumns(10);
							tfBooksAtLibrary.setBackground(new Color(224, 255, 255));
							tfBooksAtLibrary.setBounds(487, 267, 170, 20);
							adminPanelFrame.getContentPane().add(tfBooksAtLibrary);
							
							String atLibraryNumber = String.valueOf(adminPanelGetInLibraryBookNumber());
							tfBooksAtLibrary.setText(atLibraryNumber);
							
							JLabel lblAdmin = new JLabel("ADMIN");
							lblAdmin.setForeground(UIManager.getColor("Button.focus"));
							lblAdmin.setVerticalAlignment(SwingConstants.BOTTOM);
							lblAdmin.setFont(new Font("Consolas", Font.BOLD, 99));
							lblAdmin.setBounds(299, 301, 403, 114);
							adminPanelFrame.getContentPane().add(lblAdmin);
							
							JLabel lblPanel = new JLabel("PANEL");
							lblPanel.setForeground(UIManager.getColor("Button.darkShadow"));
							lblPanel.setHorizontalAlignment(SwingConstants.RIGHT);
							lblPanel.setVerticalAlignment(SwingConstants.BOTTOM);
							lblPanel.setFont(new Font("Consolas", Font.BOLD, 99));
							lblPanel.setBounds(299, 427, 403, 114);
							adminPanelFrame.getContentPane().add(lblPanel);
							
							
							
						}else {
							JOptionPane.showMessageDialog(fr, "Please be sure you entered true password.", "Wrong Password",JOptionPane.WARNING_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(fr, "There is no admin ID like this.", "Wrong ID!", JOptionPane.WARNING_MESSAGE);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
					if (!admnLoginInfo.exists())
						try {
							admnLoginInfo.createNewFile();
							FileWriter fWriter = new FileWriter(admnLoginInfo, false);
							fWriter.write("admin;admin");
							fWriter.close();
						} catch (IOException e2) {
							e2.printStackTrace();
						}
				}
			}
		});
		btnAdminLogin.setBounds(12, 164, 362, 50);
		fr.getContentPane().add(btnAdminLogin);
//________________________Librarian Panel______________________________________________________________________________________________________________________
		JButton btnLibrarianLogin = new JButton("LIBRARIAN LOGIN");
		btnLibrarianLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputLibrarianName = JOptionPane.showInputDialog("Name:",null);
				File librarianList = new File("librarian_list.txt");
				FileReader librarianListFr;
				try {
					librarianListFr = new FileReader(librarianList);
					BufferedReader librarianListBr = new BufferedReader(librarianListFr);
					String libs[] = librarianListBr.readLine().split(";");
					Boolean checker=true;
					for(int i = 0; i < libs.length; i++) {
						String[] x = libs[i].split(",");
						if(inputLibrarianName != null) {
							String inputLibrarianPass = JOptionPane.showInputDialog("Password:",null);
							if(inputLibrarianPass != null) {
								if(x[0].equals(inputLibrarianName)) {
									checker = true;
									if(x[1].equals(inputLibrarianPass)){
										fr.setVisible(false);
										JFrame LibraianPanelFrame = new JFrame();
										LibraianPanelFrame.setSize(745,600);
										LibraianPanelFrame.getContentPane().setLayout(null);
										LibraianPanelFrame.setVisible(true);
										LibraianPanelFrame.setIconImage(appLogo);
										LibraianPanelFrame.setTitle("Librarian Panel");
										
										JLabel lblLibPnlTitle = new JLabel("Librarian");
										lblLibPnlTitle.setFont(new Font("Constantia", Font.BOLD, 30));
										lblLibPnlTitle.setForeground(UIManager.getColor("Button.darkShadow"));
										lblLibPnlTitle.setHorizontalAlignment(SwingConstants.TRAILING);
										lblLibPnlTitle.setBounds(12, 12, 341, 32);
										LibraianPanelFrame.getContentPane().add(lblLibPnlTitle);
										
										JLabel lblPanel = new JLabel("Panel");
										lblPanel.setFont(new Font("Constantia", Font.BOLD, 30));
										lblPanel.setForeground(UIManager.getColor("Button.disabledText"));
										lblPanel.setBounds(378, 12, 341, 32);
										LibraianPanelFrame.getContentPane().add(lblPanel);
										
										JPanel panel_1 = new JPanel();
										panel_1.setVisible(false);
										panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
										panel_1.setBounds(12, 210, 707, 38);
										LibraianPanelFrame.getContentPane().add(panel_1);
										panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
										
										JButton btnNewMember = new JButton("New Member");
										btnNewMember.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										btnNewMember.setBounds(12, 172, 707, 26);
										btnNewMember.addActionListener(new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
												panel_1.setVisible(true);
											}
										});
										LibraianPanelFrame.getContentPane().add(btnNewMember);
										
										JPanel panel_2 = new JPanel();
										panel_2.setVisible(false);
										panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
										panel_2.setBounds(12, 298, 341, 253);
										LibraianPanelFrame.getContentPane().add(panel_2);
										panel_2.setLayout(null);
										
										JButton btnLoanBook = new JButton("Loan Book");
										btnLoanBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										btnLoanBook.setBounds(12, 260, 341, 26);
										btnLoanBook.addActionListener(new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
												panel_2.setVisible(true);
											}
										});
										LibraianPanelFrame.getContentPane().add(btnLoanBook);
										
										JPanel panel_3 = new JPanel();
										panel_3.setVisible(false);
										panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
										panel_3.setBounds(378, 298, 341, 253);
										LibraianPanelFrame.getContentPane().add(panel_3);
										panel_3.setLayout(null);
										
										JButton btnRecieveBook = new JButton("Recieve Book");
										btnRecieveBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										btnRecieveBook.setBounds(378, 260, 341, 26);
										btnRecieveBook.addActionListener(new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
												panel_3.setVisible(true);
											}
										});
										LibraianPanelFrame.getContentPane().add(btnRecieveBook);
										
										JPanel panel = new JPanel();
										panel.setVisible(false);
										panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
										panel.setBounds(12, 96, 707, 38);
										LibraianPanelFrame.getContentPane().add(panel);
										panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
										
										JButton btnNewBook = new JButton("New Book");
										btnNewBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										btnNewBook.setBounds(12, 58, 707, 26);
										btnNewBook.addActionListener(new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
												panel.setVisible(true);
											}
										});
										LibraianPanelFrame.getContentPane().add(btnNewBook);
										
										JLabel lblBookName = new JLabel("Name:");
										panel.add(lblBookName);
										
										textFieldBookName = new JTextField();
										panel.add(textFieldBookName);
										textFieldBookName.setColumns(10);
										
										JLabel lblAuthor = new JLabel("Author:");
										panel.add(lblAuthor);
										
										textFieldAuthorName = new JTextField();
										panel.add(textFieldAuthorName);
										textFieldAuthorName.setColumns(10);
										
										JLabel lblYear = new JLabel("Publication Year:");
										panel.add(lblYear);
										
										JComboBox comboBoxYear = new JComboBox();
										comboBoxYear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										comboBoxYear.setModel(new DefaultComboBoxModel(new String[] {"1800", "1801", "1802", "1803", "1804", "1805", "1806", "1807", "1808", "1809", "1810", "1811",
												"1812", "1813", "1814", "1815", "1816", "1817", "1818", "1819", "1820", "1821", "1822", "1823", "1824", "1825", "1826", "1827", "1828", "1829",
												"1830", "1831", "1832", "1833", "1834", "1835", "1836", "1837", "1838", "1839", "1840", "1841", "1842", "1843", "1844", "1845", "1846", "1847",
												"1848", "1849", "1850", "1851", "1852", "1853", "1854", "1855", "1856", "1857", "1858", "1859", "1860", "1861", "1862", "1863", "1864", "1865",
												"1866", "1867", "1868", "1869", "1870", "1871", "1872", "1873", "1874", "1875", "1876", "1877", "1878", "1879", "1880", "1881", "1882", "1883",
												"1884", "1885", "1886", "1887", "1888", "1889", "1890", "1891", "1892", "1893", "1894", "1895", "1896", "1897", "1898", "1899", "1900", "1901",
												"1902", "1903", "1904", "1905", "1906", "1907", "1908", "1909", "1910", "1911", "1912", "1913", "1914", "1915", "1916", "1917", "1918", "1919",
												"1920", "1921", "1922", "1923", "1924", "1925", "1926", "1927", "1928", "1929", "1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937",
												"1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955",
												"1956", "1957", "1958", "1959", "1960", "1961", "1962", "1963", "1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973",
												"1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991",
												"1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
												"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023"}));
										panel.add(comboBoxYear);
										
										DefaultListModel<String> bookList = new DefaultListModel();
										DefaultListModel<String> loanedBooksList = new DefaultListModel();
										JList bookListView = new JList(bookList);
										JList loanedBooksListView = new JList(loanedBooksList);
										DefaultListModel<String> memberList = new DefaultListModel();
										JList memberListView = new JList(memberList);
										
										JButton btnCommit = new JButton("Commit");
										btnCommit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										btnCommit.addActionListener(new ActionListener() {
//_____________________________________________________Saving_New_Book_________________________________________________________________________________________
											@Override
											public void actionPerformed(ActionEvent e) {
												try {
													File bookDb = new File("bookDb.txt");
													if (!bookDb.exists()) {
														bookDb.createNewFile();
													}
													
													String newBookName = textFieldBookName.getText();
													String newBookAuthor = textFieldAuthorName.getText();
													String newBookYear = comboBoxYear.getSelectedItem().toString();
													
													FileReader bookDbReader = new FileReader(bookDb);
													BufferedReader bookDbBufRead = new BufferedReader(bookDbReader);
													String oldBookDb = bookDbBufRead.readLine();
													System.out.println(oldBookDb);
													
													bookDbReader.close();
													FileWriter bookDbWriter = new FileWriter(bookDb);
													
													if (oldBookDb == null) {
														String write = newBookName + "," + newBookAuthor + "," + newBookYear + "," + "not_loaned" + ";";
														System.out.println(write + "(null)");
														bookDbWriter.write(write);
														bookDbWriter.close();
														textFieldBookName.setText("");
														textFieldAuthorName.setText("");
													} else {
														String write = oldBookDb + newBookName + "," + newBookAuthor + "," + newBookYear +  "," + "not_loaned" + ";";
														System.out.println(write + "(not null)");
														bookDbWriter.write(write);
														bookDbWriter.close();
														textFieldBookName.setText("");
														textFieldAuthorName.setText("");
													}	
													bookList.addElement(newBookName);
													bookListView.setModel(bookList);
													JOptionPane.showMessageDialog(null, "Successful", "New book saved.", JOptionPane.INFORMATION_MESSAGE);
													panel.setVisible(false);
												}
												catch (Exception e1){
													JOptionPane.showMessageDialog(null, "Something went wrong.", "ERROR", JOptionPane.ERROR_MESSAGE);
													e1.printStackTrace();
												}
											}
										});
										panel.add(btnCommit);
										
										JLabel lblMemberName = new JLabel("Name:");
										panel_1.add(lblMemberName);
										
										textFieldMemberName = new JTextField();
										textFieldMemberName.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										panel_1.add(textFieldMemberName);
										textFieldMemberName.setColumns(10);
										
										JLabel lblMemberSurname = new JLabel("Surname:");
										panel_1.add(lblMemberSurname);
										
										textFieldSurname = new JTextField();
										textFieldSurname.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										panel_1.add(textFieldSurname);
										textFieldSurname.setColumns(10);
										
										JButton btnCommit_1 = new JButton("Commit");
										btnCommit_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										btnCommit_1.addActionListener(new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
//_______________________________________________________Saving_New_Member_________________________________________________________________________________
												File memberDb = new File("memberDb.txt");
												if (!memberDb.exists()) {
													try {
														memberDb.createNewFile();
													} catch (IOException e1) {
														e1.printStackTrace();
													}
												} else {
													String newMemberName = textFieldMemberName.getText();
													String newMemberSurname = textFieldSurname.getText();
													try {
														FileReader memberDbReader = new FileReader(memberDb);
														BufferedReader memberDbBufRead = new BufferedReader(memberDbReader);
														String memberDbRead = memberDbBufRead.readLine();
														memberDbReader.close();
														
														FileWriter memberDbWriter = new FileWriter(memberDb);
														if (memberDbRead == null) {
															String write = newMemberName + "," + newMemberSurname + ";";
															memberDbWriter.write(write);
															memberDbWriter.close();
															textFieldMemberName.setText("");
															textFieldSurname.setText("");
														}else {
															String write = memberDbRead + newMemberName + "," + newMemberSurname + ";";
															memberDbWriter.write(write);
															memberDbWriter.close();
															textFieldMemberName.setText("");
															textFieldSurname.setText("");
														}
														memberList.addElement(newMemberName + "\s" +newMemberSurname);
														memberListView.setModel(memberList);
													} catch (IOException e1) {
														JOptionPane.showMessageDialog(null, "Something went wrong.", "ERROR", JOptionPane.ERROR_MESSAGE);
														e1.printStackTrace();
													}
												}
												JOptionPane.showMessageDialog(null, "Successful", "New member created.", JOptionPane.INFORMATION_MESSAGE);
												panel_1.setVisible(false);
											}
										});
										panel_1.add(btnCommit_1);	
										
										
//____________________________________________________Loaned_And_Not_Loanded_Books_Parsing_____________________________________________________________________
										File bookDb = new File("bookDb.txt");
										if(bookDb.exists()) {
											FileReader bookDbReader = new FileReader(bookDb);
											BufferedReader bookDbBufRead = new BufferedReader(bookDbReader);
											String[] books = bookDbBufRead.readLine().split(";");
											bookDbReader.close();
											for(int i1 = 0; i1 < books.length; i1++) {
												String[] bookSpecs = books[i1].split(",");
												if(bookSpecs[3].equals("not_loaned")) {
													bookList.addElement(bookSpecs[0]);
												} else if(bookSpecs[3].equals("loaned")) {
													loanedBooksList.addElement(bookSpecs[0]);
												}
											}
										}
										File memberDb = new File("memberDb.txt");
										if(memberDb.exists()) {
											FileReader memberDbReader = new FileReader(memberDb);
											BufferedReader memberDbBufRead = new BufferedReader(memberDbReader);
											String[] members = memberDbBufRead.readLine().split(";");
											for(int i1 = 0; i1 < members.length; i1++) {
												memberList.addElement(members[i1].split(",")[0] + "\s" + members[i1].split(",")[1]);
											}
										}
										
										JButton btnLoan = new JButton("Loan");
										btnLoan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										btnLoan.setBounds(113, 227, 99, 26);
										btnLoan.addActionListener(new ActionListener() {
//__________________________________________________________Loan_book_saving___________________________________________________________________________________
											@Override
											public void actionPerformed(ActionEvent e) {								
												try {
													FileReader bookDbReader = new FileReader(bookDb);
													BufferedReader bookDbBufRead = new BufferedReader(bookDbReader);
													String[] books = bookDbBufRead.readLine().split(";");
													bookDbBufRead.close();
													
													String selectedBook = bookListView.getSelectedValue().toString();
													String selectedMember = memberListView.getSelectedValue().toString();

													for(int i = 0; i < books.length; i++) {
														String[] bookSpecs = books[i].split(",");
														if(bookSpecs[0].equals(selectedBook)) {
															bookSpecs[3] = "loaned";
															books[i] = bookSpecs[0] + "," + bookSpecs[1] + "," + bookSpecs[2] + "," + bookSpecs[3];
															
															bookList.removeElementAt(bookListView.getSelectedIndex());
															loanedBooksList.addElement(selectedBook);
															bookListView.setModel(bookList);
															loanedBooksListView.setModel(loanedBooksList);
														}
													}
													FileWriter bookDbWriter = new FileWriter(bookDb);
													String writeToBookDb = "";
													for(int i = 0; i < books.length; i++) {
														writeToBookDb = writeToBookDb + books[i] + ";";
													}
													bookDbWriter.write(writeToBookDb);
													bookDbWriter.close();
													
													File loanedDb = new File("loanedDb.txt");
													if(!loanedDb.exists()) {
														loanedDb.createNewFile();
													}
													FileReader loanedDbReader = new FileReader(loanedDb);
													BufferedReader loanedBufRead = new BufferedReader(loanedDbReader);
													String loanedBooks = loanedBufRead.readLine();
													loanedBufRead.close();
													
													FileWriter loanedDbWriter = new FileWriter(loanedDb);
													if(loanedBooks == null) {
														loanedDbWriter.write(selectedBook + "," + selectedMember + ";");
													}else {
														loanedDbWriter.write(loanedBooks + selectedBook + "," + selectedMember + ";");
													}
													loanedDbWriter.close();
													
													JOptionPane.showMessageDialog(null, "Successful", "The book loaned.", JOptionPane.INFORMATION_MESSAGE);
													panel_2.setVisible(false);
												} catch (FileNotFoundException e1) {	
													JOptionPane.showMessageDialog(null, "Something went wrong.", "ERROR", JOptionPane.ERROR_MESSAGE);
													e1.printStackTrace();
												} catch (IOException e1) {
													JOptionPane.showMessageDialog(null, "Something went wrong.", "ERROR", JOptionPane.ERROR_MESSAGE);
													e1.printStackTrace();
												}
											}
										});
										panel_2.add(btnLoan);
										
										JScrollPane scrollPaneLoanBooks = new JScrollPane();
										scrollPaneLoanBooks.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										scrollPaneLoanBooks.setBounds(191, 0, 150, 222);
										panel_2.add(scrollPaneLoanBooks);
										
										scrollPaneLoanBooks.setViewportView(bookListView);
										
										JLabel lblBooksList = new JLabel("Books");
										lblBooksList.setHorizontalAlignment(SwingConstants.CENTER);
										lblBooksList.setVerticalAlignment(SwingConstants.TOP);
										scrollPaneLoanBooks.setColumnHeaderView(lblBooksList);
										
										JScrollPane scrollPaneLoanMembers = new JScrollPane();
										scrollPaneLoanMembers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										scrollPaneLoanMembers.setBounds(0, 0, 150, 222);
										panel_2.add(scrollPaneLoanMembers);
										
										scrollPaneLoanMembers.setViewportView(memberListView);
										
										JLabel lblMembersList = new JLabel("Members");
										lblMembersList.setHorizontalAlignment(SwingConstants.CENTER);
										scrollPaneLoanMembers.setColumnHeaderView(lblMembersList);
										
										JScrollPane scrollPaneRecieve = new JScrollPane();
										scrollPaneRecieve.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										scrollPaneRecieve.setBounds(0, 0, 341, 222);
										panel_3.add(scrollPaneRecieve);
										
										scrollPaneRecieve.setViewportView(loanedBooksListView);
										
										JLabel lblLoanedBooksList = new JLabel("Loaned Books");
										lblLoanedBooksList.setHorizontalAlignment(SwingConstants.CENTER);
										scrollPaneRecieve.setColumnHeaderView(lblLoanedBooksList);
										
										JButton btnRecieve = new JButton("Recieve");
										btnRecieve.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										btnRecieve.setBounds(123, 227, 99, 26);
										btnRecieve.addActionListener(new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
												
//____________________________________________________Recieve_Book_Saving_____________________________________________________________________________________
												File bookDb = new File("bookDb.txt");
												File loanedDb = new File("loanedDb.txt");
												
												try {
													FileReader bookDbRead = new FileReader(bookDb);
													FileReader loanedDbRead = new FileReader(loanedDb);
													BufferedReader bookBufRead = new BufferedReader(bookDbRead);
													BufferedReader loanedBufRead = new BufferedReader(loanedDbRead);
													
													String[] books = bookBufRead.readLine().split(";");
													String[] loanedBooks = loanedBufRead.readLine().split(";");
													bookBufRead.close();
													loanedBufRead.close();
													
													String selectedBook = loanedBooksListView.getSelectedValue().toString();
													
													for(int i = 0; i < books.length; i++) {
														String[] book = books[i].split(",");
														if(selectedBook.equals(book[0])) {
															book[3] = "not_loaned";
															books[i] = book[0] + "," + book[1] + "," + book[2] + "," + book[3];
															
															FileWriter bookDbWriter = new FileWriter(bookDb);
															String write = "";
															for(int j = 0; j < books.length; j++) {
																write = write + books[j] + ";";																
															}
															bookDbWriter.write(write);
															bookDbWriter.close();
															
															loanedBooksList.removeElementAt(loanedBooksListView.getSelectedIndex());
															bookList.addElement(selectedBook);
															bookListView.setModel(bookList);
															loanedBooksListView.setModel(loanedBooksList);
														}
													}
													
													FileWriter loanedWriter = new FileWriter(loanedDb);
													for(int i = 0; i < loanedBooks.length; i++) {
														
														String[] loanedInfo = loanedBooks[i].split(",");
														if(loanedInfo[i].equals(selectedBook)) {
															
														}else {
															loanedWriter.write(loanedBooks[i] + ";");	
														}											
													}
													loanedWriter.close();
											} catch (IOException e1) {
													// TODO Auto-generated catch block
												JOptionPane.showMessageDialog(null, "Something went wrong.", "ERROR", JOptionPane.ERROR_MESSAGE);
												e1.printStackTrace();
												}
												JOptionPane.showMessageDialog(null, "Saved.", "The Book recieved.", JOptionPane.INFORMATION_MESSAGE);
												panel_3.setVisible(false);
											}
										});
										panel_3.add(btnRecieve);
									}
									else {
										JOptionPane.showMessageDialog(null, "Check your password input", "Wrong Password!", JOptionPane.WARNING_MESSAGE);
									}
								}else checker = false;
							}
						}
					}
					if(!checker) {
						JOptionPane.showMessageDialog(null, "There is no Librarian like this.", "Wrong Librarian Name!", JOptionPane.WARNING_MESSAGE);	
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Something went wrong.", "ERROR", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		btnLibrarianLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLibrarianLogin.setFont(new Font("Carlito", Font.BOLD, 20));
		btnLibrarianLogin.setBounds(12, 272, 362, 50);
		fr.getContentPane().add(btnLibrarianLogin);
	
//____________MAIN_FRAME_LABELS______________________________________________________________________________________________________________________________
		JLabel lblLibrary = new JLabel("Library");
		lblLibrary.setForeground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
		lblLibrary.setBackground(UIManager.getColor("Button.background"));
		lblLibrary.setFont(new Font("Constantia", Font.BOLD, 30));
		lblLibrary.setBounds(12, 12, 105, 38);
		fr.getContentPane().add(lblLibrary);
		
		JLabel lblManagement = new JLabel("Management");
		lblManagement.setForeground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
		lblManagement.setFont(new Font("Constantia", Font.BOLD, 30));
		lblManagement.setBackground(UIManager.getColor("Button.background"));
		lblManagement.setBounds(12, 60, 189, 38);
		fr.getContentPane().add(lblManagement);
		
		JLabel lblSystem = new JLabel("System");
		lblSystem.setForeground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
		lblSystem.setFont(new Font("Constantia", Font.BOLD, 30));
		lblSystem.setBackground(UIManager.getColor("Button.background"));
		lblSystem.setBounds(12, 114, 102, 38);
		fr.getContentPane().add(lblSystem);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lblLogo.setIcon(new ImageIcon("C:\\Users\\gokhan.hacioglu\\eclipse-workspace\\CreatePdf\\logo.jpg"));
		lblLogo.setBounds(229, 22, 121, 122);
		fr.getContentPane().add(lblLogo);
	}
	
	public static void createNewLibrarian(File librarianList, BufferedReader librarianListBr, FileReader librarianListFr, DefaultListModel<String> libList) throws IOException {
		String setLbrnName = JOptionPane.showInputDialog("Librarian Name:", null);
		if (!(setLbrnName == null)) {
			String setLbrnPassword = JOptionPane.showInputDialog("Set Password:", null);
			libList.addElement(setLbrnName + "\sPW->\s" + setLbrnPassword);
			
			FileWriter librarianListFw = new FileWriter(librarianList);
			
			String willWrite = "";
			for(int i = 0; i<libList.size(); i++) {
				String[] willWriteSplit = libList.get(i).split("\sPW->\s");
				willWrite += willWriteSplit[0] + "," + willWriteSplit[1] + ";";
			}
			librarianListFw.write(willWrite);
			librarianListFw.close();
			
		}
	}
	public static void deleteLibrarian(JList listView, DefaultListModel<String> libList, File librarianList) {
		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure?", "Librarian will be DELETED!", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			try {
				int wantedDelete = listView.getSelectedIndex();
				libList.remove(wantedDelete);
				listView.setModel(libList);
				FileWriter librarianListFw = new FileWriter(librarianList);
				String willWrite = "";
				for(int i = 0; i<libList.size(); i++) {
					String[] willWriteSplit = libList.get(i).split("\sPW->\s");
					willWrite += willWriteSplit[0] + "," + willWriteSplit[1] + ";";
				}
				librarianListFw.write(willWrite);
				librarianListFw.close();
				JOptionPane.showMessageDialog(null, "Saved", "Librarian deleted successfully.", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Unexpected Error", "Something hapenned wrong.", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void editLibrarian(JList listView, DefaultListModel<String> libList, File librarianList) {
			try {
				String setLbrnName = JOptionPane.showInputDialog("Set New Name:", null);
				if (setLbrnName != null) {
					int wantedEdit = listView.getSelectedIndex();	
					String setLbrnPassword = JOptionPane.showInputDialog("Set New Password:", null);
					libList.remove(wantedEdit);
					String[] newParameters = {setLbrnName, setLbrnPassword};
					libList.addElement(newParameters[0] + "\sPW->\s" + newParameters[1]);
					listView.setModel(libList);
					
					FileWriter librarianListFw = new FileWriter(librarianList);
					String willWrite = "";
					for(int i = 0; i<libList.size(); i++) {
						String[] willWriteSplit = libList.get(i).split("\sPW->\s");
						willWrite += willWriteSplit[0] + "," + willWriteSplit[1] + ";";
					}
					librarianListFw.write(willWrite);
					librarianListFw.close();
					JOptionPane.showMessageDialog(null, "Saved", "Librarian deleted successfully.", JOptionPane.INFORMATION_MESSAGE);
				}			
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Unexpected Error", "Something hapenned wrong.", JOptionPane.ERROR_MESSAGE);
			}
		}
	
	public static int adminPanelGetMemberNumber() {
		File memberDb = new File("memberDb.txt");
		int memberNumber = 0;
		try {
			FileReader memberDbReader = new FileReader(memberDb);
			BufferedReader memberDbBufRead = new BufferedReader(memberDbReader);
			String read = memberDbBufRead.readLine();
			if(read != null) {
				String[] members = read.split(";");
				memberNumber = members.length;
			}
			memberDbBufRead.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memberNumber;
	}
	
	public static int adminPanelGetBookNumber() {
		File bookDb = new File("bookDb.txt");
		int bookNumber = 0;
		try {
			FileReader bookDbReader = new FileReader(bookDb);
			BufferedReader bookDbBufRead = new BufferedReader(bookDbReader);
			String read = bookDbBufRead.readLine();
			
			if(read != null) {
				String[] books = read.split(";");
				bookNumber = books.length;
			}
			bookDbBufRead.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookNumber;
	}
	
	public static int adminPanelGetLoanedBookNumber() {
		File loanedDb = new File("loanedDb.txt");
		int loanedNumber = 0;
		try {
			FileReader loanedDbReader = new FileReader(loanedDb);
			BufferedReader loanedDbBufRead = new BufferedReader(loanedDbReader);
			String read = loanedDbBufRead.readLine();
			
			if(read != null) {
				String[] loanedbooks = read.split(";");
				loanedNumber = loanedbooks.length;
			}
			loanedDbBufRead.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loanedNumber;
	}
	
	public static int adminPanelGetInLibraryBookNumber() {
		int totalBooks = adminPanelGetBookNumber();
		int loanedBooks = adminPanelGetLoanedBookNumber();
		int inLibraryBooks = totalBooks - loanedBooks;
		return inLibraryBooks;
	}
}
