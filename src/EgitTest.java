import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.RepositoryService;



public class EgitTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		GitHubClient client=new GitHubClient();
		client=client.setCredentials("gravity1989", "bangalore1");
		System.out.println("Client authenticated."+client.toString());
		/*
		 * Get repositories for user gravity1989
		*/
		/*RepositoryService service = new RepositoryService();
		for (Repository repo : service.getRepositories("gravity1989"))
		  System.out.println(repo.getName() + " Watchers: " + repo.getWatchers());
		*/
		/*
		 * List all repositories.
		*/
		RepositoryService service=new RepositoryService(client);
		System.out.println("Repository service created.");
		PageIterator<Repository> repositoryPages=service.pageAllRepositories();
		int pageNo=0;
	
		
		
		while(repositoryPages.hasNext()){
			System.out.println("pageNo:" +pageNo);
			Collection<Repository> repositoryCollection=repositoryPages.next();
			Iterator<Repository> repoIterator= repositoryCollection.iterator();
			File repositoryDataFile=new File("/home/themonkey/githubData/repoData"+pageNo+".out");
			
			ArrayList<RepositoryModel> repositoryArray=new ArrayList<RepositoryModel>();
			while(repoIterator.hasNext()){
				Repository repository=repoIterator.next();
				RepositoryModel repositoryModel=new RepositoryModel();
				RepositoryService repoService=new RepositoryService(client);
				try{
				java.util.List<Contributor> contributorList=repoService.getContributors(repository, true);
				repositoryModel.setContributorList(contributorList);
				}
				catch(org.eclipse.egit.github.core.client.RequestException re){
					continue;
				}
				repositoryModel.setRepository(repository);
				
				repositoryArray.add(repositoryModel);
				System.out.println(repository.getName()+"\t"+repository.getId());
			}
			System.out.println("*************************\n********************");
			serialize(repositoryDataFile,repositoryArray);
			pageNo++;
		}

	}
	
	public static void serialize(File outputFile,Object serializableObject) throws FileNotFoundException, IOException{
		ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(outputFile));
		oos.writeObject(serializableObject);
		oos.close();
	}
	
	private static ObjectOutputStream getOOS(File outFile) throws FileNotFoundException, IOException{
		if(outFile.exists()){
			return new AppendableObjectOutputStream(new FileOutputStream(outFile));
		}else{
			return new ObjectOutputStream(new FileOutputStream(outFile));
		}
	}
	
	
	
	public static Object deSerialize(File inputFile) throws IOException, ClassNotFoundException{
		
			FileInputStream fis=new FileInputStream(inputFile);
			ObjectInputStream ois=new ObjectInputStream(fis);
			return ois.readObject();
		
	}

}
