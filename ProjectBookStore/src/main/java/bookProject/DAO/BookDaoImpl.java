package bookProject.DAO;

import bookProject.domain.Book;
import bookProject.model.BookInfo;
import bookProject.model.Pagination;
import bookProject.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
public class BookDaoImpl implements BookDao {

    private Transaction transaction = null;

    @Override
    public Book findBook(String code) {
        List<Book> book = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        Query query = session.createQuery("From * Book t where t.id = code");
       return (Book) (book = (List<Book>) query.getSingleResult());
    }

    @Override
    public BookInfo findBookInfo(String code) {
        Book book = this.findBook(code);
        if (book == null) {
            return null;
        }
        return new BookInfo(book);
    }

    @Override
    public Pagination<BookInfo> queryBooks(int page, int maxResult, int maxNavigationPage) {
        return queryBooks(page, maxResult, maxNavigationPage, null);
    }

    @Override
    public Pagination<BookInfo> queryBooks(int page, int maxResult, int maxNavigationPage, String likeName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "Select new " + BookInfo.class.getName() //
                + "(p.code, p.nameBook, p.author,p.description, p.priceBook) " + " from "//
                + Book.class.getName() + " p ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(p.name) like :likeName ";
        }
        sql += " order by p.createDate desc ";
            Query query = session.createQuery(sql);
            if (likeName != null && likeName.length() > 0) {
                query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
            }
        return new Pagination<BookInfo>((org.hibernate.query.Query) query, page, maxResult, maxNavigationPage);
    }

    @Override
    public void save(BookInfo bookInfo) {
        String code = bookInfo.getCode();
        Book book = null;
        boolean isNew = false;
        if (code != null) {
            book = this.findBook(code);
        }
        if (book == null) {
            isNew = true;
            book = new Book();
            book.setCreateDate(new Date());
        }
        book.setId(code);
        book.setNameBook(bookInfo.getNameBook());
        book.setPriceBook(bookInfo.getPriceBook());
        if (isNew) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.persist(book);
        }
    }

    @Override
    public List<Book> allBooks(){
        List<Book> booksAll = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query query = session.createQuery("From Book");
            booksAll = (List)query.getResultList();
        }
        catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
        }
        return booksAll;
    }

    @Override
    public List<Book> search(String keyword){
        List<Book> searchBook = new ArrayList<>();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String sql = "FROM Book WHERE nameBook LIKE '%' || :keyword || '%'"
                    + " OR b.email LIKE '%' || :keyword || '%'"
                    + " OR b.address LIKE '%' || :keyword || '%'";
            Query query = session.createQuery(sql);
            searchBook = (List)query.getResultList();
        }
        catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
        }
        return searchBook;
    };
}
